package ast;

/**
 * Void (function) token in the AST
 *
 */

public class VoidTypeNode extends NodeSuper {
	@Override
	public String toPrint(String indent) {
		return indent+"VoidType\n";  
	}
  
	@Override
	public NodeSuper typeCheck() {
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}
  
}
