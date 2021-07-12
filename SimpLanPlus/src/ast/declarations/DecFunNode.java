package ast.declarations;

import java.util.ArrayList;
import java.util.List;

import ast.ArgNode;
import ast.IdNode;
import ast.Node;
import ast.statements.BlockNode;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
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
		
	    String dec = indent+"Fun: " + id.toPrint(" ") + " : " +
			   args.stream().map(arg -> "(" + arg.toPrint("") +")").reduce("",(subtotal, element) -> subtotal + ", " + element) + "->" +type.toPrint("");
	    return dec + "\n" + body.toPrint(indent);
			    
	}

	@Override
	public Node typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

}
