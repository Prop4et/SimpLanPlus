package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class AssignmentNode extends StatementNode implements Node {

	final private Node lhs;
	final private Node rhs;
	
	
	public AssignmentNode(Node lhs, Node rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	@Override
	public String toPrint(String indent) {
		return lhs.toPrint("") + " = " + rhs.toPrint("");
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
