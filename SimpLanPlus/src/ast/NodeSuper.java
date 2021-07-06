package ast;

import java.util.ArrayList;

import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public abstract class NodeSuper implements NodeInterface{

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
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
 	  	return new ArrayList<SemanticError>();
 	}
}
