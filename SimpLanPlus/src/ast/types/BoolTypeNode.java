package ast.types;

/**
 * Bool token in the AST
 *
 */
public class BoolTypeNode extends TypeNode {
  
  @Override
  public String toPrint(String indent) {
	  return indent + "BoolType\n";  
  }
    
  public TypeNode typeCheck() {
	  return null;
  }
  
  @Override
  public String codeGeneration() {
		return "";
  }
      
}  