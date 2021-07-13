package ast.expressions;

import ast.Node;

public abstract class ExpNode implements Node{

	@Override
	public String toString() {
		return toPrint("");
	}
}
