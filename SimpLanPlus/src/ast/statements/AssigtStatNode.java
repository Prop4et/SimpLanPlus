package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class AssigtStatNode extends StatementNode{
	final private AssignmentNode ass;
	
	public AssigtStatNode(final AssignmentNode ass) {
		this.ass=ass;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + ass.toPrint("");
	}

	@Override
	public TypeNode typeCheck() {
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
}
