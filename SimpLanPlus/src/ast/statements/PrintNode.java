package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import ast.types.VoidTypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class PrintNode implements Node{
	private final ExpNode exp; //ExpNode
	
	public PrintNode(final ExpNode exp) {
		this.exp = exp;
	}
	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return indent + "print: "+ exp.toPrint(indent);
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		TypeNode typeExp = exp.typeCheck();
		if( typeExp instanceof VoidTypeNode)
			throw new TypeException("Type Error: need cannot print void expressions");
		return new VoidTypeNode();
	}

	@Override
	public String codeGeneration() {
		return exp.codeGeneration()+"print $a0\n";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return exp.checkSemantics(env);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return exp.checkEffects(env);
	}

}
