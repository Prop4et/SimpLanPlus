package semanticAnalysis;

import ast.types.FunTypeNode;
import ast.types.TypeNode;
import org.stringtemplate.v4.ST;

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
		this.varStatus = new ArrayList<>();
		this.funStatus = new ArrayList<>();
		/**/
	}

	public STentry(STentry sTentry, final List<Effect> varStatus) {
		this(sTentry.getNl(), sTentry.getOffset(), sTentry.getType());
        this.funStatus = sTentry.getFunStatus();
        this.varStatus = varStatus;
	}
	
	public STentry(STentry sTentry) {
		this(sTentry.getNl(), sTentry.getOffset(), sTentry.getType());
        this.varStatus = sTentry.getVarStatus();
        this.funStatus = sTentry.getFunStatus();
	}
	public void setVarStatus(Effect varStatus, int numOfDereferentiation){
		this.varStatus.set(numOfDereferentiation, varStatus);
	}
	public void initializeStatus(){
		if ( (this.type instanceof FunTypeNode)) {
			for (TypeNode param : ((FunTypeNode) this.type).getParams()) {
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

	public List<Effect> getFunStatus(){
		return this.funStatus;
	}
	
	public List<Effect> getVarStatus() {
		return this.varStatus;
	}
	
	public Effect getIVarStatus(final int index) {
		return this.varStatus.get(index);
	}
}