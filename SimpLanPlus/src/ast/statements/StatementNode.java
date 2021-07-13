package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public abstract class StatementNode implements Node{

	@Override
	public String toString() {
		return toPrint("");
	}
}
