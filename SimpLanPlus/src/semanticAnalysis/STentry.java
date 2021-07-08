package semanticAnalysis;
import ast.Node;

/**
 * {@code STEntry} represents an entry in the Stable
 *
 */
public class STentry {
	//nesting level
	private int nl;	
	//type of the node
	private Node type;
	//offset used when generating the ASM code
	private int offset;
	
	public Node getType() {
		return type;
	}
	
	public STentry(int nl, int offset) {
		this.nl = nl;
		this.offset = offset;      
	}
	
	public STentry(int nl, int offset, Node type) {
		this.nl = nl;
		this.offset = offset;  
		this.type = type;
	}

}  