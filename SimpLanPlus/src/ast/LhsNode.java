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
		String ret="; BEGIN  DEREFERANTION NODE \n";
		if(assFlag) {
			ret += "\t mv $al $fp\n";
			for (int i = 0; i < (id.getNl() - id.getSTentry().getNl()); i++) {
                ret += "\t lw $al 0($al)\n";
            }//il 1 rappresenta il salto di al perchè l'offset è stato inizializzato considerando solo fp
			ret += " \t addi $a0 $al " +(-( id.getSTentry().getOffset()))+"\n";
		}else
			ret = id.codeGeneration();
		
		LhsNode pointer = lhs;
		while(pointer != null) { //dereference pointer
			ret += " \t lw $a0 0($a0)\n";
			pointer = pointer.lhs;
		}
			
		return ret;
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
			if(id.getSTentry().getIVarStatus(id.getTextId()).getType() == Effect.BOT)
				res.add(new SemanticError(id.getTextId() + " is used before being initialized"));
			if(id.getSTentry().getIVarStatus(id.getTextId()).getType() == Effect.DEL)
				res.add(new SemanticError(id.getTextId() + " is used after being deleted."));

			return res;
		}

		res.addAll(lhs.checkEffects(env));
		String lhsId = this.getLhsId().getTextId();
		if (! (id.getSTentry().getIVarStatus(lhs.getLhsId().getTextId()).getType() == Effect.RW))

			res.add(new SemanticError( lhsId + " has status " +this.getLhsId().getStatus().get(lhsId).getType() + " while it should have been READ_WRITE." ));

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
