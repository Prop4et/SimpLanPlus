package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class RetStatNode extends StatementNode{
	private final RetNode ret;
	
	public RetStatNode(final RetNode ret) {
		this.ret = ret;
	}
	public void setFunEndLabel(final String funEndLabel) {
		ret.setFunEndLabel(funEndLabel);
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + ret.toPrint("");
	}
	
	@Override
	public TypeNode typeCheck() throws TypeException {
		return ret.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return ret.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return ret.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return ret.checkEffects(env);
	}
}
