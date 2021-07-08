package ast.types;

import ast.Node;

/**
 * Pointer token in the AST
 *
 */
public class PointerTypeNode extends TypeNode {
  
	final TypeNode pointed;
	
	public PointerTypeNode(TypeNode pointed) {
		this.pointed = pointed;
	}
	
	public Node getType() {
		return this.pointed;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent+"^"+pointed.toPrint("");  
	}
  
	@Override
	public TypeNode typeCheck() {
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
}  