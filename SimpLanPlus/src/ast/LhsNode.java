package ast;

import java.util.ArrayList;

import ast.types.PointerTypeNode;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class LhsNode implements Node{
	private final IdNode id;
	private final LhsNode lhs;
	private boolean assFlag = false;
	
	
	public LhsNode(final IdNode id, final LhsNode lhs) {
		this.id = id;
		this.lhs = lhs;
	}
	@Override
	public String toPrint(String indent) {
		if(lhs == null)
			return indent + id.toPrint("");
		return lhs.toPrint("") + "^";
	}

	@Override
	public TypeNode typeCheck() {
		if(lhs == null)
			return id.typeCheck();
		else
			return ((PointerTypeNode) lhs.typeCheck()).getType();
	}

	@Override
	public String codeGeneration() {
		return  null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		if(lhs == null)
			return id.checkSemantics(env);
		else
			return lhs.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env)  {
		ArrayList<SemanticError> res = new ArrayList<>();

		if (lhs == null) {		//we are processing a dereference node
			res.addAll(id.checkEffects(env));
			if(id.getSTentry().getIVarStatus(0).getType() == Effect.BOT)
				res.add(new SemanticError(id.getTextId() + " is used before being initialized"));

			return res;
		}

		res.addAll(lhs.checkEffects(env));
		if (! (id.getSTentry().getIVarStatus(lhs.getDereferenceLevel() - 1).getType() == Effect.RW))
			res.add(new SemanticError( this + "  has not status READ_WRITE."));

		return res;

	}

	public IdNode getLhsId() {
		return id;
	}
	
	public void setAssignment(boolean assFlag) {
		this.assFlag = assFlag;
	}
	public int getDereferenceLevel() {
		if (lhs == null) {
			return 0;
		}

		return 1 + lhs.getDereferenceLevel();
	}


}
