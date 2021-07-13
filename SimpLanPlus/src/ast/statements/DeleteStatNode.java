package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class DeleteStatNode extends StatementNode {
	
	private DeletionNode del;
	
	public DeleteStatNode(final DeletionNode del) {
		this.del = del;
		
	}
	@Override
	public String toPrint(String indent) {
		return indent + del.toPrint("");
	}

	@Override
	public TypeNode typeCheck() {
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
