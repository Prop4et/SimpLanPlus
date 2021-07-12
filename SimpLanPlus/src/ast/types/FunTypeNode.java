package ast.types;

import java.util.List;
import java.util.stream.Stream;

import ast.Node;

public class FunTypeNode extends TypeNode{

	private final List<TypeNode> params;
	private final TypeNode ret;
	
	public FunTypeNode(final List<TypeNode> params, final TypeNode ret) {
		this.params = params;
		this.ret = ret;
	}
	
	@Override
	public String toPrint(String indent) {
		return params.stream().map(p -> p.toPrint("")).reduce(" ", (subtotal, param) -> (subtotal + ", " + param)) + " -> " + ret.toPrint("");
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
}
