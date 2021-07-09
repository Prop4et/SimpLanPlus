package ast.types;

import ast.expressions.ExpNode;

/**
 * Boolean constant in AST
 *
 */
public class BoolNode extends ExpNode {
	final private boolean val;

	public BoolNode(boolean val) {
		this.val = val;
	}
	
	public boolean getVal() {
		return val;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + val;
	}
	
	public TypeNode typeCheck() {
		return new BoolTypeNode();
	}    

	@Override
  	public String codeGeneration() {
  		//TODO
  		return null;
  	}
	
}
