package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class PrintStatNode extends StatementNode{
	private final PrintNode print;

	public PrintStatNode(final PrintNode printNode) {
		this.print= printNode;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + print.toPrint("");
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		return print.typeCheck();
	}


	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return print.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return print.checkEffects(env);
	}
}
