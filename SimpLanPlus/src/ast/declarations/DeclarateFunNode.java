package ast.declarations;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class DeclarateFunNode extends DeclarationNode{
	private final DecFunNode decFun;
	
	public DeclarateFunNode(final DecFunNode decFun) {
		this.decFun = decFun;
	}
	
	@Override
    public String toPrint(String indent) {
        return decFun.toPrint(indent);
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
