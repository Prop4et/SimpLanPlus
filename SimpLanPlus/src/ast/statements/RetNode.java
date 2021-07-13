package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class RetNode implements Node{

	private final ExpNode exp; //ExpNode
	
	
	public RetNode(final ExpNode exp) {
		this.exp = exp;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "return : " + exp.toPrint(indent);
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
