package ast.declarations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ast.ArgNode;
import ast.IdNode;
import ast.Node;
import ast.statements.BlockNode;
import ast.types.FunTypeNode;
import ast.types.TypeNode;
import exceptions.AlreadyDeclaredException;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

public class DecFunNode implements Node{
	
	private final TypeNode type;
	private final IdNode id;
	private final List<ArgNode> args;
	private final BlockNode body;
	private final FunTypeNode typeFun;



	public DecFunNode(final TypeNode type, final IdNode id, final List<ArgNode> args, BlockNode body) {
		this.type = type;
		this.id = id;
		this.args = args;
		this.body = body;

		List<TypeNode> argsType = args.stream().map(ArgNode::getType).collect(Collectors.toList());
		typeFun = new FunTypeNode(argsType, type);
	}
	
	@Override
	public String toPrint(String indent) {
		
	    String dec = indent+"Function: " + id.toPrint("") + " : " +
			   args.stream().map(arg -> "(" + arg.toPrint("") +")").reduce("",(subtotal, element) -> subtotal + "" + element + " x ");
	    dec=dec.substring(0, dec.length()-2);
	    dec += "-> " +type.toPrint("");
	    return dec + "\n" + body.toPrint(indent);
			    
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		System.out.print(body);
		if (!Node.sametype(type, body.typeCheck()))
			throw new TypeException("Type Error: Return type and function type are incompatible");
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	//should be fine for what concerns the checksemantics
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<>();
		try{
			//add function name to the environment
			env.addDec(id.getTextId(), typeFun);
			//create the new block
			env.onScopeEntry();
			//add the function to the scope for the arguments in case of (non mutual) recursion
			env.addDec(id.getTextId(), typeFun);
			//add the arguments to the new scope created
			//TODO is it right to declare new variables inside the function with the same name of the parameters?
			//if not, when body gets evaluated there shouldn't be a new scope creation
			for(ArgNode arg : args) 
				env.addDec(arg.getId().getTextId(), arg.getType());
			//body evaluation in which yet another scope is created, should we avoid this? DONE
			body.setNewScope(false);	
			//tbh here the environment in which we evaluate the body should be just the top one
			//this thing is sick tho, what's happening here is that i created a whole new environment with just the scope of the function
			//i know it's gonna be a pain later on probably, thinking about effects and stuff like that
			errors.addAll(body.checkSemantics(env));
			
			env.onScopeExit();
			
		}catch(AlreadyDeclaredException e){
			errors.add(new SemanticError(e.getMessage()));
		}
		return errors;
	}

}
