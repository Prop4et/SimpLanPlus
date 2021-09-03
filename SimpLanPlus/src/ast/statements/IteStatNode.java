package ast.statements;

import java.util.ArrayList;

import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class IteStatNode extends StatementNode{
	private final IteNode ite;
	
	public IteStatNode(final IteNode ite) {
		this.ite = ite;
	}
	
	@Override
	public String toPrint(String indent) {
		return ite.toPrint(indent);
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		return ite.typeCheck();
	}

	@Override
	public String codeGeneration() {
		ite.setFunEndLabel(getFunEndLabel());
		return ite.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return ite.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return ite.checkEffects(env);
	}
}
