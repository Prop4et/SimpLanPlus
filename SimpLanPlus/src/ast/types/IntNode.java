package ast.types;

import ast.LhsNode;
import ast.expressions.ExpNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.List;

/**
 * Int constant AST
 *
 */
public class IntNode extends ExpNode {

	private final int val;
	
	public IntNode(int val) {
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + val;
	}

	@Override
	public TypeNode typeCheck() {
		return new IntTypeNode();
	}

	@Override
	public String codeGeneration() {
		return "\t li $a0 " + val +"\n";
	}
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return new ArrayList<>();
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
