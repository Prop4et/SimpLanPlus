package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class PrintNode implements Node{
	private final Node exp; //ExpNode
	
	public PrintNode(final Node exp) {
		this.exp = exp;
	}
	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return indent + "print: "+ exp.toPrint("");
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
