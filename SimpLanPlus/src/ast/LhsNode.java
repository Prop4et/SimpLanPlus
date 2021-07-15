package ast;

import java.util.ArrayList;

import ast.types.PointerTypeNode;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class LhsNode implements Node{
	private final IdNode id;
	private final LhsNode lhs;
	private boolean assFlag = false;
	
	
	public LhsNode(final IdNode id, final LhsNode lhs) {
		this.id = id;
		this.lhs = lhs;
	}
	@Override
	public String toPrint(String indent) {
		if(lhs == null)
			return indent + id.toPrint("");
		return lhs.toPrint("") + "^";
	}

	@Override
	public TypeNode typeCheck() {
		if(lhs == null)
			return id.typeCheck();
		else
			return ((PointerTypeNode) lhs.typeCheck()).getType();
	}

	@Override
	public String codeGeneration() {
		return  null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		if(lhs == null)
			return id.checkSemantics(env);
		else
			return lhs.checkSemantics(env);
	}
	public IdNode getLhsId() {
		return id;
	}
	
	public void setAssignment(boolean assFlag) {
		this.assFlag = assFlag;
	}

}
