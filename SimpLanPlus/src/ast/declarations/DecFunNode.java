package ast.declarations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ast.ArgNode;
import ast.IdNode;
import ast.Node;
import ast.statements.BlockNode;
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
	
	public DecFunNode(final TypeNode type, final IdNode id, final List<ArgNode> args, BlockNode body) {
		this.type = type;
		this.id = id;
		this.args = args;
		this.body = body;
		
		//dovrei mettere i tipi dei parametri
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
		if(!Node.sametype(type, body.typeCheck())) 
			throw new TypeException("Return type and function type are incompatible");
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<>();
		try{
			//add function name to the environment
			env.addDec(id.getTextId(), type);
			//create the new block
			env.onScopeEntry();
			//add the arguments to the new scope created
			//TODO is it right to declare new variables inside the function with the same name of the parameters?
			//if not, when body gets evaluated there shouldn't be a new scope creation
			for(ArgNode arg : args) 
				env.addDec(arg.getId().getTextId(), arg.getType());
			//body evaluation
			errors.addAll(body.checkSemantics(env));
			env.onScopeExit();
			
		}catch(AlreadyDeclaredException e){
			errors.add(new SemanticError(e.getMessage()));
		}
		return errors;
	}

}
