package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class CallStatNode extends StatementNode{
	private final CallNode call;
	
	public CallStatNode(final CallNode call) {
		this.call = call;
	}
	
	@Override
	public String toPrint(String indent) {
		return call.toPrint(indent);
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		// TODO Auto-generated method stub
		return call.typeCheck();
	}


	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return call.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return call.checkEffects(env);
	}
}
