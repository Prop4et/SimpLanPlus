package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class AssigtStatNode extends StatementNode{
	final private AssignmentNode ass;
	
	public AssigtStatNode(final AssignmentNode ass) {
		this.ass=ass;
	}
	public void setFunEndLabel(final String funEndLabel) {
		ass.setFunEndLabel(funEndLabel);
	}
	@Override
	public String toPrint(String indent) {
		return indent + ass.toPrint("");
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		return ass.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return ass.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return ass.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return ass.checkEffects(env);
	}


}
