package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
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
		return call.checkSemantics(env);
	}	
}
