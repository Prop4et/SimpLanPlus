package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class RetStatNode extends StatementNode{
	private final RetNode ret;
	
	public RetStatNode(final Node ret) {
		this.ret = (RetNode) ret;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + ret.toPrint("");
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
