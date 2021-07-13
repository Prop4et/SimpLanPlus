package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class RetStatNode extends StatementNode{
	private final RetNode ret;
	
	public RetStatNode(final RetNode ret) {
		this.ret = ret;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + ret.toPrint("");
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
		return ret.checkSemantics(env);
	}
}
