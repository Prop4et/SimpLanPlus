package semanticAnalysis;

import java.util.ArrayList;
import java.util.HashMap;

import ast.IdNode;
import ast.Node;
import ast.types.TypeNode;
import exceptions.AlreadyDeclaredException;
import exceptions.NotDeclaredException;

public class Environment {
	
	//THESE VARIABLES SHOULDN'T BE PUBLIC
	//THIS CAN BE DONE MUCH BETTER
	
	public ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<HashMap<String,STentry>>();
	public int nl = -1;
	public int offset = 0;
	
	public Environment(ArrayList<HashMap<String, STentry>> symTable, int nl, int offset) {
		this.symTable = symTable;
		this.nl = nl;
		this.offset = offset;
	}
	
	public Environment() {
		this(new ArrayList<>(), -1, 0);
	}
	
	public Environment(Environment env) {
		this(new ArrayList<>(), env.getNestingLevel(), env.getOffset());
		
		for(HashMap<String, STentry> symTable : env.getSymTable()) {
			HashMap<String, STentry> copySymTable = new HashMap<>();
			for(String id : symTable.keySet()) {
				copySymTable.put(id, new STentry(symTable.get(id)));
			}
			this.symTable.add(copySymTable);
		}
	}
	
	public int getOffset() {
		return offset;
	}
	
	public ArrayList<HashMap<String, STentry>> getSymTable(){
		return symTable;
	}
	
	public void onScopeEntry(HashMap<String, STentry> scope) {
		symTable.add(scope);
		nl++;
		offset=0;
	}
	
	public void onScopeEntry() {
		symTable.add(new HashMap<String, STentry>());
		nl++;
		offset=0;
	}
	/**
	 * 
	 * @param id the new declaration name
	 * @param type the type of the new declaration (var or fun)
	 * @throws AlreadyDeclaredException if the top hashmap already has an entry for id
	 * @return STentry for the new declaration 
	 */
	public STentry addDec(final String id, final TypeNode type) throws AlreadyDeclaredException  {
		HashMap<String, STentry> scope = symTable.get(nl);
		STentry entry = new STentry(nl, offset, type);
		if(scope.put(id, entry) != null)
			throw new AlreadyDeclaredException("Var " + id + " was already declared.");
		offset-=4;//1?
		return entry;
	}
	
	public void printEnv() {
		int currentnl = 0;
		System.out.println("VAR TYPE OFFSET NL");
		for(HashMap<String, STentry> m : symTable) {
			//System.out.println("{");
			for(String key : m.keySet()) {
			    STentry value = m.get(key);
			    for(int i=0; i<currentnl; i++)
			    	System.out.print("\t");
			    System.out.println(key + " " + value.getType() + " " + value.getOffset() + " " + value.getNl());
			}
			//System.out.println("}");
			currentnl++;
		}
	}
	
	public void printLevel(HashMap<String, STentry> m) {
		for(String key : m.keySet()) {
		    STentry value = m.get(key);
		    System.out.println(key + " " + value.getType() + " " + value.getOffset() + " " + value.getNl());
		}
	}
	
	/**
	 * 
	 * @param id
	 * @throws NotDeclaredException if id is not declared inside the scope
	 * @return the STentry
	 *
	 */
	public STentry lookup(String id) throws NotDeclaredException {
		//better that way idk
		int i = nl;
		STentry var = null;
		while(i>-1 && var == null){
			HashMap<String, STentry> scope = symTable.get(i);	
			//get returns null if there's no mapping for the key
			var = scope.get(id);
			i--;
		}
		if(var == null)
			throw new NotDeclaredException("Missing declaration for var: " + id);
		return var;
	}

	public STentry lookupForEffectAnalysis(String id) {
		//better that way idk
		int i = nl;
		STentry var = null;
		while(i>-1 && var == null){
			HashMap<String, STentry> scope = symTable.get(i);
			//get returns null if there's no mapping for the key
			var = scope.get(id);
			i--;
		}
		if(var != null)
			return var;
		else
			System.out.print("Cannot find the " + id +" into the symbol table. There is something wrong. " );
		return  null;
	}
	
	public void onScopeExit() {
		this.symTable.remove(nl);
		nl--;
	}

	public int getNestingLevel(){
		return nl;
	}	
	
	//method used for example where an if is involved 
	//if then else means there's the need to take the max effect for each variable in both environments
	/*public Environment max(Environment e1, Environment e2) {
		return null;
	}*/
	public void addEntry(String id, STentry sTentry){
		symTable.get(nl).put(id, sTentry);		//adding
	}

}
