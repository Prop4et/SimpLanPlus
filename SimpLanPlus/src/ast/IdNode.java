package ast;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class IdNode implements Node {
    private final String id;
    private STentry entry;
    private int nestinglevel;

    public IdNode(final String id) {
        this.id = id;
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
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        try {
            this.entry = env.lookup(id);    //throw NotDeclaredException if lookup return null
            nestinglevel = env.getNestingLevel();
            
        }catch (NotDeclaredException e){
            res.add(new SemanticError(e.getMessage()));
        }


        return res;
    }

}
