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
		return "push "+(val?1:0)+"\n";
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
