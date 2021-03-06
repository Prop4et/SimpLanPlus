package semanticAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.util.function.BiFunction;

import ast.IdNode;
import ast.types.FunTypeNode;
import ast.types.TypeNode;
import exceptions.AlreadyDeclaredException;
import exceptions.NotDeclaredException;

public class Environment {
	
	public List<HashMap<String,STentry>> symTable;
	public int nl ;
	public int offset = 0;
	
	public Environment(List<HashMap<String, STentry>> symTable, int nl, int offset) {
		this.symTable = symTable;
		this.nl = nl;
		this.offset = offset;
	}
	
	public Environment() {
		this(new ArrayList<>(), -1, 0);
	}
	
	public Environment(Environment env) {
		this(new ArrayList<>(), env.getNestingLevel(), env.getOffset());
		//up to this should be fine
		for (HashMap<String, STentry> scope : env.symTable) {
            final HashMap<String, STentry> copy = new HashMap<>();
            for (String id : scope.keySet()) {
                copy.put(id, new STentry(scope.get(id)));
            }
            this.symTable.add(copy);
        }
	}
	//used to create a toy environment for the update
	public Environment(String id, STentry entry) {
		this.symTable = new ArrayList<>();
		this.nl = 0;
		this.offset = 0;
		//for(int i = 0; i < this.symTable.size(); i++)
		HashMap<String, STentry> tmp = new HashMap<>();
		tmp.put(id, entry);
		this.symTable.add(tmp);
	}
	
	/**
	 * 
	 * @param env1 the first environment
	 * @param env2 the second environment (a subset of the first)
	 * @return a new environment obtained with {@code maxOrSeq} with the max function
	 */
	public static Environment max(final Environment env1, final Environment env2) {
		return maxOrSeq(env1, env2, Effect::max);
	}
	
	/**
	 * 
	 * @param env1 the first environment
	 * @param env2 the second environment (a subset of the first)
	 * @return a new environment obtained with {@code maxOrSeq} with the seq function
	 */
	public static Environment seq(final Environment env1, final Environment env2) {
		return maxOrSeq(env1, env2, Effect::seq);
	}
	
	
	/**
	 * 
	 * @param env1 first environment
	 * @param env2 second environment
 	 * @param fn function to apply
	 * @return the new environment with the correct value for the effect given the function
	 * 
	 */
	public static Environment maxOrSeq(Environment env1, Environment env2, BiFunction<Effect, Effect, Effect> fn){
		Environment resEnv=new Environment(new ArrayList<>(), env1.getNestingLevel(), env1.offset);
		for(int i = 0; i < env1.getSymTable().size(); i++) {//cycling through all the scopes
			HashMap<String,STentry> scopeEnv1 = env1.getSymTable().get(i);
			HashMap<String,STentry> scopeEnv2;
			if(env2.getSymTable().size() > 1 )
				scopeEnv2 = env2.getSymTable().get(i);
			else
				scopeEnv2 = env2.symTable.get(0);		//bcs when using seq we take seq(Eps, [x->effect]), so the second env is composed of a single scope
			HashMap<String,STentry> resScope = new HashMap<>();
			for(String id : scopeEnv1.keySet()) {//cycling through all the variables inside the scope
				STentry entryEnv1 = scopeEnv1.get(id);
				STentry entryEnv2 = scopeEnv2.get(id);
				//id \in scopeEnv2
				if(entryEnv2 != null) {
					STentry resEntry = new STentry(entryEnv1);
					for(int j = 0; j < entryEnv1.getVarStatus().size(); j++) {
						resEntry.setVarStatus(id, fn.apply(entryEnv1.getIVarStatus(id), entryEnv2.getIVarStatus(id)));

					}
					resScope.put(id, resEntry);
				}
				else
					resScope.put(id, entryEnv1);
			}
			resEnv.symTable.add(resScope);
		}
		return resEnv;
	}
	//creating the environment with the single entry to apply seq
	public void applySeq(IdNode id, int effectToSet ){
		Environment newEnv = new Environment();
		newEnv.onScopeEntry();
		STentry existingEntry = this.lookupForEffectAnalysis(id.getTextId());
		STentry tmp = new STentry(existingEntry.getNl(),existingEntry.getOffset(),existingEntry.getType());
		tmp.initializeStatus(id);
		tmp.setVarStatus(id.getTextId() ,new Effect(effectToSet));
		newEnv.addEntry(id.getTextId(), tmp);
		Environment seqEnv = Environment.seq(this, newEnv);
		this.replace(seqEnv);
	}
	public void applyPar(IdNode id, Effect formalParamEffect, Effect actualParamEffect){

	}
	/**
	 * the two environment should be different, par is applied to the top of each environment
	 * since par is used only for function invocations
	 * 
	 * @param env1 
	 * @param env2
	 * @return
	 */

	public static Environment par(final Environment env1, final Environment env2) {
		Environment resEnv = new Environment();
		HashMap<String,STentry> scopeEnv1 = env1.getSymTable().get(env1.getSymTable().size()-1);
		HashMap<String,STentry> scopeEnv2 = env2.getSymTable().get(env2.getSymTable().size()-1);
		resEnv.onScopeEntry();
		//i'm pretty sure there's a smarter way to do this btw
		for(String id : scopeEnv1.keySet()) {
			STentry entryEnv1 = scopeEnv1.get(id);
			// id \not in Sigma'
			if(!scopeEnv2.containsKey(id)) {
				STentry newEntry = new STentry(entryEnv1);
				resEnv.addEntry(id, newEntry);
			}
		}
		
		for(String id : scopeEnv2.keySet()) {
			STentry entryEnv2 = scopeEnv2.get(id);
			// id \not in Sigma
			if(!scopeEnv1.containsKey(id)) {
				STentry newEntry = new STentry(entryEnv2);
				resEnv.addEntry(id, newEntry);
			}
		}
		
		for(String id : scopeEnv1.keySet()) {
			STentry entryEnv1 = scopeEnv1.get(id);
			STentry entryEnv2 = scopeEnv2.get(id);
			// id \in Sigma & \in Sigma' 
			if(entryEnv2 != null) {
				STentry resEntry = new STentry(entryEnv2);
				for(int j = 0; j < entryEnv1.getVarStatus().size(); j++) {
					resEntry.setVarStatus(id, Effect.par(entryEnv1.getIVarStatus(id), entryEnv2.getIVarStatus(id)));
				}
				resEnv.addEntry(id, resEntry);
			}
		}
		
		return resEnv;
	}
	
	/**
	 * applies the updates in env2 on env1, every time a new 'u' is found it will
	 * be removed from env2, update is recursively applied
	 * 
	 * @param env1 starting environment
	 * @param env2 environment with new values, it gets modified during the method execution
	 * @return resEnv the updated environment
	 */

	public static Environment update(Environment env1, Environment env2) {
		Environment resEnv = null;
		if (env2.symTable.size() == 0 || env1.symTable.size() == 0) {
            return new Environment(env1);
        }
		
		HashMap<String, STentry> topScope = env1.getSymTable().get(env1.getSymTable().size()-1);
		HashMap<String, STentry> scopeEnv2 = env2.getSymTable().get(env2.getSymTable().size()-1);
		
		//\sigma' is empty
		if(scopeEnv2.keySet().isEmpty()) 
			return new Environment(env1);
		
		
		//there's something inside env2
		Entry<String, STentry> t = scopeEnv2.entrySet().stream().findFirst().get();
	
		env2.removeUpdate(t.getKey());
		//id \in Sigma1
		if(topScope.containsKey(t.getKey())) {
			topScope.put(t.getKey(), t.getValue());
			resEnv = update(env1, env2);
		}
		//id \not in Sigma1
		else {
			//[u->a], i need a new environment for that
			Environment singleEnv = new Environment(t.getKey(), t.getValue());
			//should get out of the current scope
			env1.onScopeExit();
			Environment insideUpdateEnv = update(env1, singleEnv);
			//once the update is done we put the top scope back in 
			insideUpdateEnv.onScopeEntry(topScope);
			//then we can proceed with the outer update
			resEnv = update(insideUpdateEnv, env2);
		}
		return resEnv;
	}
	
	/**
	 * removes an id and its entry from the environment
	 * used when an update is required and an entry needs to be removed from env2
	 * 
	 * @param id the identifier that needs to be removed
	 */

	private void removeUpdate(String id) {
		for (int i = symTable.size() - 1; i >= 0; i--) {
            if (symTable.get(i).containsKey(id)) {
                symTable.get(i).remove(id);
                return;
            }
        }
		
	}
	
	public int getOffset() {
		return offset;
	}
	
	public List<HashMap<String, STentry>> getSymTable(){
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
		offset-=1;
		return entry;
	}
	
	public void printEnv() {
		int currentnl = 0;
		System.out.println("VAR TYPE OFFSET NL 			EFFECT ");

		for(HashMap<String, STentry> m : symTable) {
			for(String key : m.keySet()) {
			    STentry value = m.get(key);
			    for(int i=0; i<currentnl; i++)
			    	System.out.print("\t");
			    if(value.getType() instanceof FunTypeNode) {
					String res = key + " " + value.getType() + " " + value.getOffset() + " " + value.getNl() + " ";
					String formattationStr = "-> ";
					for ( HashMap<String,Effect> input: value.getFunStatus()) {
						for (String argKey : input.keySet())
							res = res + argKey + ":  " + input.get(argKey).getType() + ", ";
						res = res + formattationStr;
						formattationStr = " ";
					}
					System.out.print(res + "\n");
				}
				else
				System.out.println(key + " " + value.getType() + " " + value.getOffset() + " " + value.getNl() + " " +  value.getIVarStatus(key).getType());
			}
			currentnl++;
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
	//we're using lookupforEffectAnalysis when analysing effect, at this point we're sure that variables/ functions are declared properly otherwise, the execution would have stopped at the semantic analysis
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
		if(nl >= 0) {
			int maxOffset = 0; //offset is a negative value
			HashMap<String,STentry> entry = symTable.get(nl);
			for(String key : entry.keySet()) {
				if(maxOffset > entry.get(key).getOffset())
					maxOffset = entry.get(key).getOffset();
			}
			offset = maxOffset;	
		}
			
	}

	public int getNestingLevel(){
		return nl;
	}	
	
	//method used for example where an if is involved 
	//if then else means there's the need to take the max effect for each variable in both environments
	
	public void addEntry(String id, STentry sTentry){
		symTable.get(nl).put(id, sTentry);		//adding
	}

	public void replace(Environment env){
		this.symTable.clear();
		//this.symTable = env.getSymTable();		//replace the for loop
		this.nl = env.getNestingLevel();
		this.offset = env.getOffset();
		for (HashMap<String, STentry> scope : env.symTable) {
            final HashMap<String, STentry> copy = new HashMap<>();
            for (String id : scope.keySet()) {
                copy.put(id, new STentry(scope.get(id)));
            }
            this.symTable.add(copy);
        }

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Environment env = (Environment) obj;

		if (nl != env.nl) {
			return false;
		}

		if (offset != env.offset) {
			return false;
		}

		if (symTable.size() != env.symTable.size()) {
			return false;
		}

		for (int i = 0; i < symTable.size(); i++) {
			HashMap<String, STentry> scope = symTable.get(i);
			HashMap<String, STentry>  scope2 = env.symTable.get(i);

			if (!scope2.keySet().equals(scope.keySet())) {
				return false;
			}

			for (Map.Entry<String,STentry> entry : scope2.entrySet()) {
				STentry entryEnv = scope.get(entry.getKey());
				if (!entry.getValue().equals(entryEnv)) {
					return false;
				}
			}
		}

		return true;
	}
}
