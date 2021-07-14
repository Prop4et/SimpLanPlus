package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
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
	public TypeNode typeCheck() throws TypeException {
		return del.typeCheck();
	}


	@Override
	public String codeGeneration() {
		return del.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return del.checkSemantics(env);
	}	
}
