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
	private String label; //label 
	
	public RetNode(final ExpNode exp) {
		this.exp = exp;
	}
	
	public void setLabel(String label) {
		this.label = label;
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
		String ret = "";
		if(exp != null)
			ret = exp.codeGeneration();
		ret += "b " + label + "\n";
		return ret;
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
		if (exp == null){
			return new ArrayList<>();
		}

		return exp.checkEffects(env);
	}

}
