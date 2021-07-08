package ast.types;

/**
 * Int token in the AST
 *
 */
public class IntTypeNode extends TypeNode {
  
	@Override
	public String toPrint(String indent) {
		return indent+"IntType\n";  
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