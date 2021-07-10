package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class DeleteStatNode extends StatementNode {
	
	private DeletionNode del;
	
	public DeleteStatNode(final Node del) {
		this.del = (DeletionNode) del;
		
	}
	@Override
	public String toPrint(String indent) {
		return indent + del.toPrint("");
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
		return del.checkSemantics(env);
	}	
}
