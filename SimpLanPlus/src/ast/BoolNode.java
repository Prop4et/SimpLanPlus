package ast;

/**
 * Boolean constant in AST
 *
 */
public class BoolNode extends NodeSuper{
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
	
	public NodeSuper typeCheck() {
		return new BoolTypeNode();
	}    

	@Override
  	public String codeGeneration() {
  		//TODO
  		return null;
  	}
	
}
