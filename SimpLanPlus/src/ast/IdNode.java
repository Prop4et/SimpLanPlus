package ast;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
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
    public Node typeCheck() {
        return null;
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
        /*int j=env.nl;
        STentry tmp=null;
        while (j>=0 && tmp==null)
            tmp=(env.symTable.get(j--)).get(id);
        if (tmp==null)
            res.add(new SemanticError("Id "+id+" not declared"));

        else{
            entry = tmp;
            nestinglevel = env.nestingLevel;
        }
        ####from simplan /*

         */

        //create result list
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        this.entry = env.lookup(id);    //throw NotDeclaredException if lookup return null
        nestinglevel = env.getNestingLevel();

        return res;
    }

}
