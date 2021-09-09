package ast.statements;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;

public abstract class StatementNode implements Node{
	private String funEndLabel;
	private boolean function;
	
	@Override
	public String toString() {
		return toPrint("");
	}
	
	protected void setFunEndLabel(final String funEndLabel) {
		this.funEndLabel = funEndLabel;
	}
	
	
	public String getFunEndLabel() {
		return this.funEndLabel;
	}
	
	
	public abstract TypeNode typeCheck() throws TypeException;

	public void setFunction(boolean function) {
		this.function = function;
	}
	
	public boolean getFunction() {
		return function;
	}
}
