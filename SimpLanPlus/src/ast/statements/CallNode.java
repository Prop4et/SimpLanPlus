package ast.statements;

import java.util.ArrayList;

import java.util.List;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class CallNode implements Node{

	private final Node id; //IdNode?
	private final List<Node> params;//ExpNode
	
	public CallNode(final Node id, final List<Node> params) {
		this.id = id;
		this.params=params;
	}
	
	@Override
	public String toPrint(String indent) {
		//cos in teoria stampa pi volte lo stesso parametro se viene passato con aliasing
		String s = "";
		for(Node p : params) {
			s += p.toPrint("");
		}
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
