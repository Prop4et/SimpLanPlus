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
	private String funEndLabel=""; //label is set inside decfunnode when generating the code
	
	public RetNode(final ExpNode exp) {
		this.exp = exp;
	}
	public String getFunEndLabel() {
		return this.funEndLabel;
	}
	
	public void setFunEndLabel(String funEndLabel) {
		this.funEndLabel = funEndLabel;
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
		String ret = "; RETURN "  + "\n" ;
		if(exp != null)
			ret += exp.codeGeneration();
		ret += "\t b " + funEndLabel + "\n";
		ret += ";END RETURN " + "\n";
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
