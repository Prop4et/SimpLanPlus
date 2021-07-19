package ast.types;

import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

/**
 * Void (function) token in the AST
 *
 */

public class VoidTypeNode extends TypeNode {
	@Override
	public String toPrint(String indent) {
		return indent+"void";  
	}
  
	@Override
	public TypeNode typeCheck() {
		//it's actually a no type
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

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return  new ArrayList<SemanticError>();
	}


}
