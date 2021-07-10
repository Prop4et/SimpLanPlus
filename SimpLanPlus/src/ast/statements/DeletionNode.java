package ast.statements;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class DeletionNode extends StatementNode implements Node {
	final private IdNode id;//idNode?
	
	public DeletionNode(IdNode id) {
		this.id = id;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "delete :" + id.toPrint("");
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

		res.addAll(id.checkSemantics(env));
		return res;
	}
}
