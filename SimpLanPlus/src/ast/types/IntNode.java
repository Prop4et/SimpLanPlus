package ast.types;

import ast.expressions.ExpNode;

/**
 * Int constant AST
 *
 */
public class IntNode extends ExpNode {

	private final int val;
	
	public IntNode(int val) {
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + val;
	}

	@Override
	public TypeNode typeCheck() {
		return new IntTypeNode();
	}

	@Override
	public String codeGeneration() {
		//TODO
		return null;
	}

}
