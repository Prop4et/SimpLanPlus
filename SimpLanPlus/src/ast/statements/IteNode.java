package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class IteNode implements Node{
	private final Node cond; //ExpNode
	private final StatementNode thenB;//then branch is a statement
	private final StatementNode elseB;//else branch is another statement
	
	public IteNode(final Node cond, final Node thenB, final Node elseB) {
		this.cond = (Node) cond;//Expnode
		this.thenB = (StatementNode) thenB;
		this.elseB = (StatementNode) elseB;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "if cond: " + cond.toPrint("") + "\n" + indent + "then: " + thenB.toPrint("") + 
				(elseB != null ? indent + "else: " + elseB.toPrint("") : "");
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
