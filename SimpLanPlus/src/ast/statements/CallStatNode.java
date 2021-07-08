package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class CallStatNode extends StatementNode{
	private final CallNode call;
	
	public CallStatNode(final Node call) {
		this.call = (CallNode) call;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + call.toPrint("");
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
