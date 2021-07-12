package ast;

import java.util.ArrayList;

import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class ArgNode implements Node{
	private final TypeNode type;
	private final IdNode id;
	
	public ArgNode(final TypeNode type, final IdNode id) {
		this.type = type;
		this.id = id;
	}
	@Override
	public String toPrint(String indent) {
		return indent + id.toPrint("") + ":" + type.toPrint(indent);
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
