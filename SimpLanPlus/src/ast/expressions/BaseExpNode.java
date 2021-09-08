package ast.expressions;

import ast.IdNode;
import ast.LhsNode;
import ast.Node;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import exceptions.TypeException;
import org.stringtemplate.v4.ST;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseExpNode extends ExpNode {
    final private ExpNode exp;

    public BaseExpNode(ExpNode exp){
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "(" + exp.toPrint(indent) + ")";
    }

    @Override
    public TypeNode typeCheck() throws TypeException {
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(exp.checkSemantics(env));
        return res;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        /*               ids(e)={x 1 ,...,x n }
              -------------------------------------------
                   ∑ ⊢ e : ∑ ⊳[x 1 ⟼ rw,..., x n ⟼ rw]              */
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(exp.checkEffects(env));
        //getting all the variables that appear inside the expression  ->   ids(e)={x 1 ,...,x n }
        List<LhsNode> expVar = getExpVar();
        //apply seq with Rw effect for each variable
        for (LhsNode var: expVar){
            env.applySeq(var.getLhsId(), Effect.RW);
            STentry seqEntry = env.lookupForEffectAnalysis(var.getLhsId().getTextId());

            if(seqEntry.getIVarStatus(var.getLhsId().getTextId() ).getType() == Effect.TOP)
                res.add(new  SemanticError("Variable " + var.getLhsId().getTextId() + " is used after deletion."));
        }


        return res;
    }


    @Override
    public List<LhsNode> getExpVar() {
        return exp.getExpVar();
    }

}
