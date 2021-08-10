package ast.types;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

/**
 * Pointer token in the AST
 *
 */
public class PointerTypeNode extends TypeNode {
  
	final TypeNode pointed;
	
	public PointerTypeNode(TypeNode pointed) {
		this.pointed = pointed;
	}
	
	public TypeNode getType() {
		return this.pointed;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent+"^"+pointed.toPrint("");  
	}
  
	@Override
	public TypeNode typeCheck() {
		//falls on others
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}
  
  
  	@Override
  	public boolean equals(Object obj) {
  			if(this == obj)
  				return true;
  			if(obj == null || this.getClass() != obj.getClass())
  				return false;
  			PointerTypeNode toCompare = (PointerTypeNode) obj;
  			if(!pointed.equals(toCompare.pointed))
  				return false;
  			return true;
	  	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return new ArrayList<SemanticError>();
	}  //???

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return null;
	}
	@Override
	public int getDereferenceLevel() {
		return 1 + pointed.getDereferenceLevel();
	}


}  