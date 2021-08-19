package ast.statements;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.types.PointerTypeNode;
import ast.types.TypeNode;
import ast.types.VoidTypeNode;
import exceptions.TypeException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

public class DeletionNode implements Node {
	final private IdNode id;//idNode?
	
	public DeletionNode(IdNode id) {
		this.id = id;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "delete :" + id.toPrint(indent);
	}


	@Override
	public TypeNode typeCheck() throws TypeException {
		//ID can be a variables, a function or a pointer
		TypeNode type = id.typeCheck();

		return new VoidTypeNode();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> res = new ArrayList<>();
		res.addAll(id.checkSemantics(env));
		return res;
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		/*
				----------------------------------
					∑ ⊢ delete x; : ∑ ⊳[x ⟼ d]				*/
		ArrayList<SemanticError> res = new ArrayList<>();
		res.addAll(id.checkSemantics(env));


		//environment update
		if(id.getSTentry().getIVarStatus(0).getType() == Effect.DEL)
			res.add(new SemanticError("Variable " + id.getTextId() + " was already deleted."));
		else {

			env.applySeq(id, Effect.DEL);
		}
		return res;
	}

}
