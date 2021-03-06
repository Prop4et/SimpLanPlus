package ast.types;

import ast.LhsNode;
import ast.expressions.ExpNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.List;

/**
 * Boolean constant in AST
 *
 */
public class BoolNode extends ExpNode {
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
		int val = this.val ? 1 : 0;
		return "\t li $a0 " + val +"\n";
	}
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return new ArrayList<SemanticError>();
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return  new ArrayList<SemanticError>();
	}

	@Override
	public List<LhsNode> getExpVar() {
		return new ArrayList<>();
	}

}
