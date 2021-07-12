package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class BlockStatNode extends StatementNode{
	private final BlockNode block;
	
	public BlockStatNode(final Node block) {
		this.block =(BlockNode) block;
	}
	
	@Override
	public String toPrint(String indent) {
		return block.toPrint(indent);
	}

	@Override
	public Node typeCheck() {
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
