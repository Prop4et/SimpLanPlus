package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import ast.types.VoidTypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class RetNode implements Node{

	private final ExpNode exp; //ExpNode
	
	
	public RetNode(final ExpNode exp) {
		this.exp = exp;
	}
	
	@Override
	public String toPrint(String indent) {
		if(exp != null)
			return indent + "return : " + exp.toPrint(indent);
		return indent + "return : ";
	}

	@Override
	public TypeNode typeCheck()  throws TypeException {
		if(exp == null) {
			return new VoidTypeNode();
		}
		return exp.typeCheck();
	}


	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		if (exp == null){
			return new ArrayList<>();
		}
		return exp.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return null;
	}

}
