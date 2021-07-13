package ast.types;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public abstract class TypeNode implements Node{

	@Override
	public String toString() {
		return toPrint("");
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || this.getClass() != obj.getClass())
			return false;
		return true;
	}
}
