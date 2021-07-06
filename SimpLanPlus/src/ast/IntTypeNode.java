package ast;

/**
 * Int token in the AST
 *
 */
public class IntTypeNode extends NodeSuper {
  
	@Override
	public String toPrint(String indent) {
		return indent+"IntType\n";  
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