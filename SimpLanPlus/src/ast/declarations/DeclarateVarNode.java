package ast.declarations;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class DeclarateVarNode extends DeclarationNode{
	private final DecVarNode decVar;
	
	public DeclarateVarNode(final DecVarNode decVar) {
		this.decVar = decVar;
	}
	
	 @Override
	    public String toPrint(String indent) {
	        return decVar.toPrint(indent);
	    }

	    @Override
	    public Node typeCheck() {
	        return null;
	    }

	    @Override
	    public String codeGeneration() {
	        return null;
	    }

	    @Override
	    public ArrayList<SemanticError> checkSemantics(Environment env) {
	        return null;
	    }
}
