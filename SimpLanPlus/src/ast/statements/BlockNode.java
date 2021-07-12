package ast.statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import ast.Node;
import ast.declarations.DeclarationNode;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

public class BlockNode implements Node{

	final private List<DeclarationNode> decs;
	final private List<StatementNode> stms;
	
	public BlockNode(final List<DeclarationNode> decs, final List<StatementNode> stms) {
		this.decs=decs;
		this.stms=stms;
	}
	@Override
	public String toPrint(String indent) {
		String declarationsToPrint = decs.stream().map((dec1) -> dec1.toPrint(indent + "  ")).reduce("",
				(dec1, dec2) -> (dec1.isEmpty() ? dec1 : (dec1 + "\n")) + dec2);
		String statementsToPrint = stms.stream().map((stm1) -> stm1.toPrint(indent + "  ")).reduce("",
				(stm1, stm2) -> (stm1.isEmpty() ? stm1 : (stm1 + "\n")) + stm2);

		return indent + "{\n" + (declarationsToPrint.isEmpty() ? "" : (declarationsToPrint + "\n")) + (statementsToPrint.isEmpty() ? "" : (statementsToPrint + "\n")) + indent + "}";
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
		env.onScopeEntry();

		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//check semantics in the dec list
		if(decs.size() > 0){
			env.offset = -2;
			//if there are children then check semantics for every child and save the results
			for(DeclarationNode n : decs)
				res.addAll(n.checkSemantics(env));
		}
		//---------------------------- Più tardi
		if(stms.size() > 0){
			env.offset = -2;
			//if there are children then check semantics for every child and save the results
			for(StatementNode n : stms)
				res.addAll(n.checkSemantics(env));
		}

		//clean the scope, we are leaving a let scope
		env.onScopeExit();

		//return the result
		return res;
	}

}
