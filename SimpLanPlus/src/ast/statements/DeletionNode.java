package ast.statements;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
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
		return id.typeCheck();
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
}
