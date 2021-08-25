package ast;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IdNode implements Node {
    private final String id;
    private STentry entry;
    private int nl;

    public IdNode(final String id) {
        this.id = id;
    }
    
    public void setSTentry(STentry entry) {
    	this.entry = entry;
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + id;
    }

    @Override
    public TypeNode typeCheck() {
        return entry.getType();
    }

    @Override
    public String codeGeneration() {
        String getAR="";
        for (int i=0; i<nl-entry.getNl(); i++)
            getAR+="lw\n";
        return "push "+entry.getOffset()+"\n"+ //metto offset sullo stack
                "lfp\n"+getAR+ //risalgo la catena statica
                "add\n"+
                "lw\n"; //carico sullo stack il valore all'indirizzo ottenuto

    }
    
    public String getTextId() {
    	return this.id;
    }
    
    public STentry getSTentry() {
    	return entry;
    }
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        try {
            this.entry = env.lookup(id);    //throw NotDeclaredException if lookup return null
            nl = env.getNestingLevel();
        }catch (NotDeclaredException e){
            res.add(new SemanticError(e.getMessage()));
        }
        return res;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        //lookup fun throws not declared exceptiom, however this kind of exception should have been handled by the semantic analysis
        //So, in theory exception should never be thrown at this point
        entry = env.lookupForEffectAnalysis(id);

        nl = env.getNestingLevel();
        return new ArrayList<>();
    }

    public void setStatus(Effect status){
        entry.setVarStatus(id, status );
    }
    public HashMap<String, Effect>getStatus(){
        return entry.getVarStatus();
    }
}
