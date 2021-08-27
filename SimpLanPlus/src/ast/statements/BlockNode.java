package ast.statements;

import java.util.ArrayList;
import java.util.List;


import ast.Node;
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
		if(newScope) {//this means we are not inside a function 
			if(main) //
				ret += "\t push $sp\n";//just for consistency, to have the same stack structure everywhere
			else if(!function){
				ret += "\t push $fp\n"; //if is not a function fp needs to be saved cause it's not saved otherwise, inside a function the callee pushes the fp 
			}
			ret += "\t mv $al $fp\n \t push $al\n";//used to go through the static chain
		}
				
		for(DeclarationNode dec : decs)
			ret += dec.codeGeneration();
		for(StatementNode stm : stms)
			ret += stm.codeGeneration();
		
		if(main)
			ret += "\t halt\n";
		
		return ret;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		if(newScope)
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