package ast.types;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class FunTypeNode extends TypeNode{

	private final List<TypeNode> params;
	private final TypeNode ret;
	
	public FunTypeNode(final List<TypeNode> params, final TypeNode ret) {
		this.params = params;
		this.ret = ret;
	}
	
	public List<TypeNode> getParams() {
		return params;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + params.stream().map(p -> p.toPrint("")).reduce(" ", (subtotal, param) -> (subtotal + ", " + param)) + " -> " + ret.toPrint("");
	}
	public TypeNode getReturnedValue(){
		return ret;
	}

	@Override
	public TypeNode typeCheck() {
		//nothing to return, it falls on other types 
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return new ArrayList<SemanticError>();
    }
}
