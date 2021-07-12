package ast.statements;

import java.util.ArrayList;

import java.util.List;

import ast.IdNode;
import ast.Node;
import ast.expressions.ExpNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class CallNode implements Node{
	private final IdNode id;

	private final List<ExpNode> params;//ExpNode
	
	public CallNode(final IdNode id, final List<ExpNode> params) {
		this.id = id;
		this.params=params;
	}
	
	@Override
	public String toPrint(String indent) {
		//cos in teoria stampa pi volte lo stesso parametro se viene passato con aliasing
		String s = "(";
		for(ExpNode p : params) {
			s += p.toPrint("") + ", ";
		}
		s=s.substring(0, s.length()-2);
		s += ")";
		return indent + "call: " + id.toPrint("") + s; 
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
