package ast.prog;

import java.util.List;

import ast.Node;
import ast.declarations.DeclarationNode;
import ast.statements.StatementNode;

import java.util.ArrayList;

import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class BlockNode implements Node{
	
	final private List<DeclarationNode> decs;
	final private List<StatementNode> stms;
	
	public BlockNode(List<DeclarationNode> decs, List<StatementNode> stms) {
		this.decs = decs;
		this.stms = stms;
	}
	
	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return null;
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

}
