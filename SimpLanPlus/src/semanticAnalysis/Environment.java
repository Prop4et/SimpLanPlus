package semanticAnalysis;

import java.util.ArrayList;
import java.util.HashMap;

import ast.Node;

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
	 * @param Id
	 * @return false if already declared, true otherwise
	 */
	public void addDec(String id, Node type)/* throws AlreadyDeclaredException*/  {
		HashMap<String, STentry> scope = symTable.get(nl);
		if(scope.put(id, new STentry(nl, offset, type)) != null)
			/*throw exception*/;
		offset-=4;//1?		
	}
	
	/**
	 * 
	 * @param symTable, Id
	 * @return null se non è dichiarata, tipo altrimenti
	 * oppure 
	 * potrebbe ritornare l'eccezione se non è dichiarata e il tipo altrimenti
	 */
	public Node lookup(String id) /*throws DeclarationException*/ {
		//più bello while magari così mi fermo appena lo trovo
		int i = nl;
		Node var = null;
		while(i>-1 && var == null){
			HashMap<String, STentry> scope = symTable.get(nl);
			if(scope.containsKey(id))
				var = scope.get(id).getType();
		}
		/*if(var == null)
			throw DeclaredException*/
		return var;
	}
	
	public void onScopeExit() {
		this.symTable.remove(nl);
		nl--;
	}
}
