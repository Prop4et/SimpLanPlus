package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import exceptions.TypeException;
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
        return null;
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
        //getting variables    ->   ids(e)={x 1 ,...,x n }
        Environment newEnv = new Environment();
        newEnv.onScopeEntry();
        List<LhsNode> expVar = getExpVar();
        for (LhsNode var: expVar){
            STentry existingEntry = env.lookupForEffectAnalysis(var.getLhsId().getTextId());
            STentry tmp = new STentry(existingEntry.getNl(),existingEntry.getOffset(),existingEntry.getType());
            tmp.initializeStatus();
            tmp.setVarStatus(new Effect(Effect.RW),0);
            newEnv.addEntry(var.getLhsId().getTextId(), tmp);
            Environment seqEnv = Environment.seq(env, newEnv);
            STentry seqEntry = seqEnv.lookupForEffectAnalysis(var.getLhsId().getTextId());
            if(seqEntry.getIVarStatus(var.getDereferenceLevel() ).getType() == Effect.TOP)
                res.add(new  SemanticError("Variable " + var.getLhsId().getTextId() + "is used after deletion"));
            env.replace(seqEnv);
        }


        return res;
    }

    @Override
    public List<LhsNode> getExpVar() {
        return exp.getExpVar();
    }

}
