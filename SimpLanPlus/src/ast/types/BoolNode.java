package ast.types;

/**
 * Boolean constant in AST
 *
 */
public class BoolNode extends TypeNode{
	final private boolean val;

	public BoolNode(boolean val) {
		this.val = val;
	}
	
	public boolean getVal() {
		return val;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + val;
	}
	
	public TypeNode typeCheck() {
		return new BoolTypeNode();
	}    

	@Override
  	public String codeGeneration() {
  		//TODO
  		return null;
  	}
	
}
