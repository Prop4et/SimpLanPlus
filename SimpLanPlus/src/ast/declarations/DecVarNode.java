package ast.declarations;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class DecVarNode implements Node{
	private final TypeNode type;
	private final IdNode id;
	private final ExpNode exp;
	
	public DecVarNode(final TypeNode type, final IdNode id, final ExpNode exp) {
		this.type = type;
		this.id = id;
		this.exp = exp;
	}
	
	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return null;
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
