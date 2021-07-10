package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.expressions.ExpNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class IteNode implements Node{
	private final ExpNode cond; //ExpNode
	private final StatementNode thenB;//then branch is a statement
	private final StatementNode elseB;//else branch is another statement
	
	public IteNode(final ExpNode cond, final StatementNode thenB, final StatementNode elseB) {
		this.cond = cond;
		this.thenB = thenB;
		this.elseB = elseB;
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
		//create the result
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//check semantics in the condition
		res.addAll(cond.checkSemantics(env));

		//check semantics in the then and in the else exp
		res.addAll(thenB.checkSemantics(env));
		res.addAll(elseB.checkSemantics(env));

		//bisogna incrementare/decrementare  il nestinglevel quando entriamo/usciamo dai rami then/else?

		return res;
	}

}
