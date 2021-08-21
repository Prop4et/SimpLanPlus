package semanticAnalysis;

import ast.ArgNode;
import ast.IdNode;
import ast.Node;
import ast.declarations.DecFunNode;
import ast.types.FunTypeNode;
import ast.types.TypeNode;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.HashMap;
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

	private DecFunNode funNode;

	public HashMap<String, Effect> varStatus;
	public List<HashMap<String,Effect> > funStatus;


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
		this.varStatus = new HashMap<>();
		this.funStatus = new ArrayList<>();
	}
	
	public STentry(int nl, int offset, TypeNode type) {
		this.nl = nl;
		this.offset = offset;  
		this.type = type;
		this.varStatus =  new HashMap<>();
		this.funStatus =  new ArrayList<>();

	}

	public STentry(STentry sTentry, final  HashMap<String, Effect> varStatus) {
		this(sTentry.getNl(), sTentry.getOffset(), sTentry.getType());
        this.funStatus = sTentry.getFunStatus();
        this.varStatus = varStatus;
	}
	
	public STentry(STentry sTentry) {
		this(sTentry.getNl(), sTentry.getOffset(), sTentry.getType());
        this.varStatus = sTentry.getVarStatus();
        this.funStatus = sTentry.getFunStatus();
	}
	public void setVarStatus(String id, Effect varStatus){
		this.varStatus.put(id, varStatus);
	}
	public void setArgsStatus(int paramIndex, Effect argStatus, int numOfDereferentiation){
		funStatus.get(1).get(paramIndex).setType(argStatus.getType());

	}

	public  DecFunNode getFunNode(){
		return funNode;
	}

	public void setFunNode(DecFunNode funNode) {
		this.funNode = funNode;
	}

	public void initializeStatus(IdNode id){

		if ( (this.type instanceof FunTypeNode)) {
			HashMap<String, Effect> paramStatus = new HashMap<>();

			for (ArgNode param : this.getFunNode().getArgs()) {
				//	int numberOfDereference = param.getDereferenceLevel();
				//	for (int i = 0; i < numberOfDereference; i++) {
						paramStatus.put(param.getId().getTextId(), new Effect(Effect.BOT));

				//	}

				//	this.funStatus.add(paramStatus);
				}
			this.funStatus.add(paramStatus);
			this.funStatus.add(paramStatus);     

			//funStatus.stream().forEach(s->s.stream().forEach(s1 -> System.out.print(s1.getType() )));

		}
		else{
			//int numberOfDereference = type.getDereferenceLevel();
			//for (int i = 0; i < numberOfDereference; i++) {
			this.varStatus.put(id.getTextId(), new Effect(Effect.BOT));
			//}
		}
	}

	public List<HashMap<String,Effect> > getFunStatus(){

		return this.funStatus;
	}
	
	public HashMap<String, Effect> getVarStatus() {                                   //secondo me si può togliere
		return this.varStatus;
	}
	
	public Effect getIVarStatus(final String key) {
		return this.varStatus.get(key);
	}
}