package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class IteStatNode extends StatementNode{
	private final IteNode ite;
	
	public IteStatNode(final Node ite) {
		this.ite = (IteNode) ite;
	}
	
	@Override
	public String toPrint(String indent) {
		return ite.toPrint(indent);
	}

	@Override
	public TypeNode typeCheck() {
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
		return ite.checkSemantics(env);
	}
}
