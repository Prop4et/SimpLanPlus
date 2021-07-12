package ast;

import java.util.ArrayList;

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
	public Node typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}
	public IdNode getLhsId() {
		return id;
	}
	
	public void setAssignment(boolean assFlag) {
		this.assFlag = assFlag;
	}

}
