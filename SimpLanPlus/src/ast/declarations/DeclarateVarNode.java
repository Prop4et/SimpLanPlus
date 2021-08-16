package ast.declarations;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import exceptions.TypeException;
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
	    public TypeNode typeCheck() throws TypeException {
	        return decVar.typeCheck();
	    }

	    @Override
	    public String codeGeneration() {
	        return decVar.codeGeneration();
	    }

	    @Override
	    public ArrayList<SemanticError> checkSemantics(Environment env) {
	        return decVar.checkSemantics(env);
	    }

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return decVar.checkEffects(env);
	}
}
