package ast;

/**
 * Bool token in the AST
 *
 */
public class BoolTypeNode extends NodeSuper {
  
  @Override
  public String toPrint(String indent) {
	  return indent + "BoolType\n";  
  }
    
  public NodeSuper typeCheck() {
	  return null;
  }
  
  @Override
  public String codeGeneration() {
		return "";
  }
      
}  