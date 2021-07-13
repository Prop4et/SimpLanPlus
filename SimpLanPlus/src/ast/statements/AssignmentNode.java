package ast.statements;

import java.util.ArrayList;

import ast.LhsNode;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import exceptions.TypeException;
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
		return indent + lhs.toPrint("") + " = " + rhs.toPrint("");
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		TypeNode lhsType = lhs.typeCheck();
		TypeNode rhsType = rhs.typeCheck();

		if(!Node.sametype(lhsType, rhsType))
			throw new TypeException("Type Error: " + lhs.getLhsId() + "is of type " + lhsType + "; cannot assign "+ rhs +" of type " + rhsType + ".");
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
