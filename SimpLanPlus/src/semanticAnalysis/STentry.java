package semanticAnalysis;
import ast.Node;
import ast.types.TypeNode;

/**
 * {@code STEntry} represents an entry in the Stable
 *
 */
public class STentry {
	//nesting level
	private int nl;	
	//type of the node
	private TypeNode type;
	//offset used when generating the ASM code
	private int offset;
	
	public TypeNode getType() {
		return type;
	}
	
	public STentry(int nl, int offset) {
		this.nl = nl;
		this.offset = offset;      
	}
	
	public STentry(int nl, int offset, TypeNode type) {
		this.nl = nl;
		this.offset = offset;  
		this.type = type;
	}

	public int getOffset() {
		return offset;
	}
	
	public int getNl() {
		return nl;
	}
}  