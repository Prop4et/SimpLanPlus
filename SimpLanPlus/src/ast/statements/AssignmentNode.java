package ast.statements;

import java.util.ArrayList;

import ast.LhsNode;
import ast.Node;
import ast.expressions.ExpNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class AssignmentNode extends StatementNode implements Node {

	final private LhsNode lhs;
	final private ExpNode rhs;
	
	
	public AssignmentNode(LhsNode lhs, ExpNode rhs) {
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
		ArrayList<SemanticError> res = new ArrayList<>();

		res.addAll(lhs.checkSemantics(env));
		res.addAll(rhs.checkSemantics(env));

		return res;
	}

}
