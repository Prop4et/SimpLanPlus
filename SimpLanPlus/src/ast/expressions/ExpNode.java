package ast.expressions;

import ast.LhsNode;
import ast.Node;

import java.util.List;

public abstract class ExpNode implements Node{

	@Override
	public String toString() {
		return toPrint("");
	}
	public abstract List<LhsNode> getExpVar();
}
