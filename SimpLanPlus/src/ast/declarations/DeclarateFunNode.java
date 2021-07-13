package ast.declarations;

import java.util.ArrayList;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
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
    public TypeNode typeCheck() throws TypeException {
        return decFun.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return decFun.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return decFun.checkSemantics(env);
    }
}
