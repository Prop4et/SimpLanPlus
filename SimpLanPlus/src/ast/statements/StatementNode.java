package ast.statements;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;

public abstract class StatementNode implements Node{

	@Override
	public String toString() {
		return toPrint("");
	}
	
	public abstract TypeNode typeCheck() throws TypeException;
}
