package ast.statements;

import java.util.ArrayList;

import ast.IdNode;
import ast.LhsNode;
import ast.Node;
import ast.expressions.BaseExpNode;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import ast.types.VoidTypeNode;
import exceptions.TypeException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
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
			throw new TypeException("Type Error: " + lhs.getLhsId().getTextId() + " is of type " + lhsType + "; cannot assign "+ rhs +" of type " + rhsType + ".");
		return null;
	}

	@Override
	public String codeGeneration() {
		return  "; BEGIN " + 	this.toPrint("") + "\n" +
				"" + rhs.codeGeneration() +
				"\t push $a0\n" +
				"" + lhs.codeGeneration() +
				"\t lw $t1 0($sp)\n" +
				"\t pop\n" +
				"\t sw $t1 0($a0)\n" +
				"; END " + 	this.toPrint("") + "\n";
				
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> res = new ArrayList<>();

		res.addAll(lhs.checkSemantics(env));
		res.addAll(rhs.checkSemantics(env));

		return res;
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		/*					∑ ⊢ rhs : ∑'
				------------------------------------[Asgn-e]
					∑ ⊢ lhs = rhs; : ∑' ⊳[lhs ⟼ rw]					*/
		ArrayList<SemanticError> res = new ArrayList<>();

		ArrayList<SemanticError> expError = rhs.checkEffects(env);		//getting ∑' from rhs analysis
		//if lhs is a variable,we set its effect to rw easily

		env.applySeq(lhs.getLhsId(), Effect.RW);

		if(! expError.isEmpty()) {
			res.add(new SemanticError("Trying to assign a bad expression: "));
			res.addAll(expError);
		}

		return res;
	}

}
