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
        return null;
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
    public ArrayList<SemanticError> checkEffects(Environment env) throws NotDeclaredException{
        //lookup fun throws not declared exceptiom, however this kind of exception should have been handled by the semantic analysis
        //So, in theory exception should never be thrown at this point
        entry = env.lookup(id);

        nl = env.getNestingLevel();
        return null;
    }

    public void setStatus(Effect status){
        entry.varStatus = status;
    }

}
