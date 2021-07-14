package semanticAnalysis;

import java.util.ArrayList;
import java.util.HashMap;

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
	public void addDec(final String id, final TypeNode type) throws AlreadyDeclaredException  {
		HashMap<String, STentry> scope = symTable.get(nl);
		if(scope.put(id, new STentry(nl, offset, type)) != null)
			throw new AlreadyDeclaredException(id + " already declared");
		offset-=4;//1?		
	}
	
	/**
	 * 
	 * @param id
	 * @return null se non � dichiarata, tipo altrimenti
	 * oppure 
	 * potrebbe ritornare l'eccezione se non � dichiarata e il tipo altrimenti
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
			throw new NotDeclaredException("The variable " + id + " wasn't declared before");
		return var;
	}
	
	public void onScopeExit() {
		this.symTable.remove(nl);
		nl--;
	}

	public int getNestingLevel(){
		return nl;
	}
}
