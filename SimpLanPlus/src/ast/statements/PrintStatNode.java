package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class PrintStatNode extends StatementNode{
	private final PrintNode print;

	public PrintStatNode(Node print) {
		this.print=(PrintNode) print;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + print.toPrint("");
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
