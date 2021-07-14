package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class BlockStatNode extends StatementNode{
	private final BlockNode block;
	
	public BlockStatNode(final BlockNode block) {
		this.block = block;
	}
	
	@Override
	public String toPrint(String indent) {
		return block.toPrint(indent);
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		return block.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return block.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return block.checkSemantics(env);
	}	
}
