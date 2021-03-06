package ast;

import java.util.ArrayList;

import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class ArgNode implements Node{
	private final TypeNode type;
	private final IdNode id;
	
	public ArgNode(final TypeNode type, final IdNode id) {
		this.type = type;
		this.id = id;
	}
	@Override
	public String toPrint(String indent) {
		return indent + id.toPrint("") + ":" + type.toPrint(indent);
	}

	public IdNode getId() {
		return id;
	}
	
	public TypeNode getType(){
		return type;
	}
	
	@Override
	public TypeNode typeCheck() throws TypeException {
		return null; //falls back on other stuff
	}

	@Override
	public String codeGeneration() {
		String begin = "; BEGIN " + this + "\n";
		String end = "; END " + this + "\n";
		return begin + "addi $sp $sp 1 ; allocates space on the stack for the argument " + id + "\n" + end;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return new ArrayList<>();
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		return new ArrayList<>();
	}


}
