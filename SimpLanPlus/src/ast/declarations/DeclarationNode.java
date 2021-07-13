package ast.declarations;

import ast.Node;

public abstract class DeclarationNode implements Node {
	
    public String toString() {
    	return toPrint("");
    }
}
