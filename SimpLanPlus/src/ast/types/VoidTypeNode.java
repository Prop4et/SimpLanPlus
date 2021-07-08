package ast.types;

/**
 * Void (function) token in the AST
 *
 */

public class VoidTypeNode extends TypeNode {
	@Override
	public String toPrint(String indent) {
		return indent+"VoidType\n";  
	}
  
	@Override
	public TypeNode typeCheck() {
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}
  
}
