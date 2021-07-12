package ast.types;

import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

/**
 * Bool token in the AST
 *
 */
public class BoolTypeNode extends TypeNode {
  
  @Override
  public String toPrint(String indent) {
	  return indent + "bool";  
  }
    
  public TypeNode typeCheck() {
	  return null;
  }
  
  @Override
  public String codeGeneration() {
		return "";
  }

  @Override
  public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }


}  