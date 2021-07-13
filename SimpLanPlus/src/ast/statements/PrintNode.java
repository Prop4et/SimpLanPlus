package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class PrintNode implements Node{
	private final ExpNode exp; //ExpNode
	
	public PrintNode(final ExpNode exp) {
		this.exp = exp;
	}
	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return indent + "print: "+ exp.toPrint(indent);
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
		return exp.checkSemantics(env);
	}

}
