package ast.statements;

import java.util.ArrayList;
import java.util.List;


import ast.Node;
import ast.declarations.DeclarateFunNode;
import ast.declarations.DeclarateVarNode;
import ast.declarations.DeclarationNode;
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

		for(StatementNode stm : stms) {
			stm.setFunEndLabel(funEndLabel);
		}
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
		if (newScope) {
			if(main) {
				ret += "push $sp\n";
			}
			else{
				ret += "push $fp ;push old fp\n";
				ret += "push $cl\n";

			}
			//pushing ra so the stack is always consistent

			ret += "subi $sp $sp 1; ra \n";

			ret += "mv $al $fp\n";
			ret += "push $al ;it's equal to the old $fp\n";
			if (main) {
				ret += "mv $fp $sp; bring up the frame pointer\n";
				ret += "sw $fp 0($fp); save the old value\n"; //maybe not useful
			}
		}
		List<DeclarationNode> varDecs = new ArrayList<>();
		List<DeclarationNode> funDecs = new ArrayList<>();

		for(DeclarationNode d : decs) {
			if(d instanceof DeclarateVarNode)
				varDecs.add(d);
			if(d instanceof DeclarateFunNode)
				funDecs.add(d);
		}
		//generate code for declarations

		for(DeclarationNode d : varDecs){
			ret += d.codeGeneration();
		}

		if (newScope && !main) {
			ret += "mv $fp $sp; frame pointer above the new declarations\n";
			ret += "addi $fp $fp " + varDecs.size() + " ;frame pointer before decs (n =: " + varDecs.size()+")\n";
		}
		//generate statements
		for(StatementNode s : stms)
			ret += s.codeGeneration();


		if (main) {
			ret += "halt\n";
		}

		if (newScope && !main) {
			//pop all the declarations
			ret += "addi $sp $sp " + varDecs.size() + " ;pop var declarations\n"; // Pop var declarations.
			ret += "pop ;pop $al\n";
			ret += "pop ;pop consistency ra\n";
			ret += "lw $cl 0($sp)\n";
			ret += "pop\n";
			ret += "lw $fp 0($sp) ;restore old $fp\n";
			ret += "pop ;pop old $fp\n";
		}
		//function declaration at the end, they need the space for ra
		for(DeclarationNode f : funDecs)
			ret += f.codeGeneration();
		ret += "; END BLOCK\n";
		return ret;
	}


	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		if (newScope) {
			env.onScopeEntry();
		}
		//check semantics in the dec list
		if (!decs.isEmpty()) {
			for (DeclarationNode d : decs)
				res.addAll(d.checkSemantics(env));
		}

		//if we're inside the body of a function we shouldn't be able to take ids from outside the function params and definitions inside the function
		if (!stms.isEmpty()) {
			//i'm inside the body of a function
			if(function) {
				
					stms.add(new RetStatNode(new RetNode(null)));
				for (StatementNode s : stms) {
					//if there's an if statement i need to flag this if cause it's inside a function, it has to have a return and can crate a new block
					if(s instanceof IteStatNode)
						//the statement inside the if is itself a part of the body of the function, this will call the setFunction, which
						//sets the statement as a function body part, if i find a new block inside the if it's flagged as a function body part
						s.setFunction(true);
				}
			}else {
				//if the body is not a function and a return statement is found then it's an error
				for (StatementNode s : stms) 
					if(s instanceof RetStatNode) 
						res.add(new SemanticError("cannot use return inside an inner block"));
			}
			
			//statement evaluation is done afterwards
			for (StatementNode s : stms) 
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
		if(newScope) {
			env.onScopeEntry();				//creation of Eps ° []  environment

		}
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
	
	public boolean getMain() {
		return this.main;
	}
}