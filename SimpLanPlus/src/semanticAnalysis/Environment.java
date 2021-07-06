package semanticAnalysis;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	public void onScopeEntry() {
		symTable.add(new HashMap<String, STentry>());
		nl++;
	}
	/**
	 * 
	 * @param Id
	 * @return false if already delcared, true otherwise
	 */
	public boolean onDeclaration(String Id) {
		HashMap<String, STentry> scope = symTable.get(nl);
		if(!scope.containsKey(Id))
			return false;
		scope.put(Id, new STentry(nl, offset));
		offset+=4;
		return true;			
	}
	
	/**
	 * 
	 * @param Id
	 * @return true se è già stata dichiarata e quindi utilizzabile, false altrimenti
	 */
	public boolean onUse(String Id) {
		//più bello while magari così mi fermo appena lo trovo
		int i = nl;
		boolean declared = false;
		while(i>-1 && !declared){
			HashMap<String, STentry> scope = symTable.get(nl);
			if(scope.containsKey(Id))
				declared = true;
		}
		return declared;
	}
	
	public void onScopeExit() {
		symTable.remove(nl);
		nl--;
	}
}
