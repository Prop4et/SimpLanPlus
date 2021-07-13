package ast.types;

import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

/**
 * Int token in the AST
 *
 */
public class IntTypeNode extends TypeNode {
  	
	@Override
	public String toPrint(String indent) {
		return indent+"int";  
	}
  
	@Override
	public TypeNode typeCheck() {
		//watch for IntNode
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