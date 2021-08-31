package ast.statements;

import java.util.ArrayList;
import java.util.List;


import ast.Node;
import ast.declarations.DeclarateFunNode;
import ast.declarations.DeclarateVarNode;
import ast.declarations.DeclarationNode;
import ast.expressions.BaseExpNode;
import ast.types.TypeNode;
import ast.types.VoidTypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class BlockNode implements Node{

	final private List<DeclarationNode> decs;
	final private List<StatementNode> stms;

	//avoid creating another scope if inside a function
	private boolean newScope;
	//body function
	private boolean function;
	//main
	private boolean main;
	//in case of function this is the end label
	private String funEndLabel;
	
	public BlockNode(final List<DeclarationNode> decs, final List<StatementNode> stms) {
		this.decs=decs;
		this.stms=stms;
		this.newScope = true;
		this.function = false;
	}
	@Override
	public String toPrint(String indent) {
		String declarationsToPrint = decs.stream().map((dec1) -> dec1.toPrint(indent + "  ")).reduce("",
				(dec1, dec2) -> (dec1.isEmpty() ? dec1 : (dec1 + "\n")) + dec2);
		String statementsToPrint = stms.stream().map((stm1) -> stm1.toPrint(indent + "  ")).reduce("",
				(stm1, stm2) -> (stm1.isEmpty() ? stm1 : (stm1 + "\n")) + stm2);

		return indent + "{\n" + (declarationsToPrint.isEmpty() ? "" : (declarationsToPrint + "\n")) + (statementsToPrint.isEmpty() ? "" : (statementsToPrint + "\n")) + indent + "}";
	}

	public void setFunEndLabel(final String funEndLabel) {
		for(StatementNode stm : stms) 
			stm.setFunEndLabel(funEndLabel);
	}
	
	@Override
	public TypeNode typeCheck() throws TypeException {
		
		Boolean returnFlag = false; 
		//first check that declarations and statements inside the block are typed correctly
		for (DeclarationNode dec : decs) {
			dec.typeCheck();
		}
		for (StatementNode stm : stms) {
			stm.typeCheck();
			if(stm instanceof RetStatNode)
				returnFlag = true;
		}
		if (stms.size() == 0) {		//no statements
			return new VoidTypeNode();
		}

		if (!returnFlag) {//no return statement in the block
			//there's a chance of finding ite stms which could have returns inside them
			List<StatementNode> iteStatNodes = new ArrayList<>();
			for(StatementNode stm : stms)
				if(stm instanceof IteStatNode)
					iteStatNodes.add(stm);
			//check the return type of ite nodes
			if(!iteStatNodes.isEmpty()) {
				for(int i = 0; i<iteStatNodes.size()-1; i++) {
					if(!Node.sametype(iteStatNodes.get(i).typeCheck(), iteStatNodes.get(i+1).typeCheck())) {
						throw new TypeException("Return statement inside if with incompatible types");
					}
				}
				//everything has the same type so we return the first one (there's for sure at least one cause itestatnodes is not empty)
				return iteStatNodes.get(0).typeCheck();
			}
			//no return found
			return new VoidTypeNode();
		}
		//System.out.print("size "+ stms.size());

		return stms.get(stms.size()-1).typeCheck();	//return at the end of the block, we checked before that there will be no code after a return
		// shouldn't be any other node in which a return can appear

	}

	@Override
	public String codeGeneration() {
		//block could be a function body or a normal block, or the main
		//ra in function right?
		String ret = "; NEW BLOCK \n";
		
		if(newScope) {
			ret += "\t push $fp\n ";
			if(!main) {
				ret += "mv $fp $sp\n";
			}
				ret += "\t lw $al 0($fp)\n";
				ret += "\t push $al\n";		//in this way al point to the old fp, and we're able to get it when exit scope
				//nel caso del main block la prima cella resta vuota, perchè fp ad offset 0 contiene 0 inizialmente
		}
		List<DeclarationNode> funDeclarations = new ArrayList<>();
		for (DeclarationNode dec : decs) {
			if (( dec instanceof DeclarateFunNode)) {
				funDeclarations.add(dec);
			}
			ret += dec.codeGeneration();
		}
		
		for(StatementNode stm : stms) 
			ret += stm.codeGeneration();
		
		if(newScope) {
			if(!main) {
				for (int i = 0; i< decs.size(); i++)		//pop for var
					ret += "pop\n";
				ret += "\t pop \n";		//pop of al and fp
				ret += "lw $fp 0($sp) \n ";
				ret += "pop\n";			//pop old fp
			}
			//else {
				//ret += "\t pop\n";

			//}
		}
		//we need to put function declaration at the end of the assembly code, otherwise they refer to register that aren't yet been initialized by the callee, for example the ra register
		for(DeclarationNode dec: funDeclarations)
			ret += dec.codeGeneration();
		
		if(main)
			ret += "\t halt\n";
		
		return ret;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		if(newScope)
			if(main)
				env.onScopeEntry();
			else
				env.onScopeEntry();
		//check semantics in the dec list
		if(!decs.isEmpty()){
			for(DeclarationNode d : decs) 
				res.addAll(d.checkSemantics(env));		
		}

		//if we're inside the body of a function we shouldn't be able to take ids from outside the function params and definitions inside the function
		
		if(!stms.isEmpty()){
			for(StatementNode s : stms) 
				res.addAll(s.checkSemantics(env));			
		}

		//check if there's something after return
		for(StatementNode s : stms) {
			if (s instanceof RetStatNode) {
				if(stms.indexOf(s) + 1 < stms.size())
					res.add(new SemanticError("There's code after a return statement"));
			}

		}
		//clean the scope, we are leaving a let scope
		if(newScope)
			env.onScopeExit();

		//return the result
		return res;
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		/* Block Inference Rule:
		Eps°[] |- D: Eps'		Eps'|- S: Eps''
		Eps'' = Eps''_0 ° Eps''_1  		  (	Eps''_1 <= d)foreach x in dom(Eps''_1)
		----------------------------------------------------------------------------
								Eps |- {D S}: Eps''_0
		 */


		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		if(newScope)
			env.onScopeEntry();				//creation of Eps ° []  environment
		//check Effect  in the dec list
		if(!decs.isEmpty()){				//creation of Eps' environment
			for(DeclarationNode d : decs)		//
				res.addAll(d.checkEffects(env));
		}

		if(!stms.isEmpty()){							//creation of Eps'' environment. Needs to check the assumptions of the inference rules over Eps'' in s.checkeffect
			for(StatementNode s : stms)
				res.addAll(s.checkEffects(env));
		}

		//clean the scope, we are leaving a let scope
		if(newScope)
			env.onScopeExit();

		//return the result
		return res;
	}
	
	public void setFunction(boolean function) {
		this.function = function;
	}

	public void setNewScope(boolean newScope) {
		this.newScope = newScope;
	}
	
	public void setMain(boolean main) {
		this.main = main;
	}
}