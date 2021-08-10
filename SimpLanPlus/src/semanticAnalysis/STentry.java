package semanticAnalysis;

import ast.types.FunTypeNode;
import ast.types.TypeNode;

import java.util.ArrayList;
import java.util.List;

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

	public List<Effect> varStatus;
	public List<Effect> funStatus;


	public TypeNode getType() {
		return type;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getNl() {
		return nl;
	}
	
	public STentry(int nl, int offset) {
		this.nl = nl;
		this.offset = offset;
		this.varStatus = new ArrayList<>();
		this.funStatus = new ArrayList<>();
	}
	
	public STentry(int nl, int offset, TypeNode type) {
		this.nl = nl;
		this.offset = offset;  
		this.type = type;

		if ( (type instanceof FunTypeNode)) {
			for (TypeNode param : ((FunTypeNode) type).getParams()) {
				List<Effect> paramStatus = new ArrayList<>();
				int numberOfDereference = param.getDereferenceLevel();
				for (int i = 0; i < numberOfDereference; i++) {
					paramStatus.add(new Effect(Effect.BOT));
				}
				this.funStatus.add((Effect) paramStatus);
			}
		}
		else{
			int numberOfDereference = type.getDereferenceLevel();
			for (int i = 0; i < numberOfDereference; i++) {
				this.varStatus.add(new Effect(Effect.BOT));
			}
		}
	}

	public STentry(STentry sTentry) {
		this(sTentry.getNl(), sTentry.getOffset());
        this.type = sTentry.getType();
	}
	public void setStatus(Effect varStatus, int numOfDereferentiation){
		this.varStatus.set(numOfDereferentiation, varStatus);
	}

	
}