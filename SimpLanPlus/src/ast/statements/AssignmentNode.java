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

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		/*					∑ ⊢ rhs : ∑'
				------------------------------------[Asgn-e]
					∑ ⊢ lhs = rhs; : ∑' ⊳[lhs ⟼ rw]					*/
		ArrayList<SemanticError> res = new ArrayList<>();
		//res.addAll(lhs.checkEffects(env));
		res.addAll(rhs.checkEffects(env));		//∑'
		//if lhs is a variable,we set its effect to rw easily
		IdNode var = null;
		//if(rhs.().getSTentry().getIVarStatus(lhs.getLhsId().getTextId()).getType() == Effect.TOP){
		//	res.add(new SemanticError("Trying to assign variable with status " + lhs.getLhsId().getSTentry().getIVarStatus(lhs.getLhsId().getTextId()).getType()));
		//}
		env.applySeq(lhs.getLhsId(), Effect.RW);
		for (int i =0; i<rhs.getExpVar().size(); i++){
			var = rhs.getExpVar().get(i).getLhsId();
			if(! (var.getSTentry().getIVarStatus(var.getTextId()).getType() == Effect.RW)) {
				//env.applySeq(lhs.getLhsId(), Effect.TOP);        //se mi trovo in uno di questi casi setto lhs a TOP?

				res.add(new SemanticError("Trying to assign a bad expression: "));
			}
		}

		env.printEnv();
		//if lhs is a pointer
		//-----;

		return res;
	}

}
