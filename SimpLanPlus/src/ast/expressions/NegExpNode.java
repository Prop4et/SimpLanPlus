package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.types.BoolTypeNode;
import ast.types.IntTypeNode;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class NegExpNode extends ExpNode{
    final private ExpNode exp;

    public NegExpNode(ExpNode exp){
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "-" + exp.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeException {
        if( exp.typeCheck() instanceof BoolTypeNode)
            throw new TypeException("Type Error: Bool expression cannot have negative sign.");
        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() +  "\t multi $a0 $a0 -1 \n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(exp.checkSemantics(env));

        return res;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(exp.checkEffects(env));

        //getting variables    ->   ids(e)={x 1 ,...,x n }
        List<LhsNode> expVar = getExpVar();

        for (LhsNode var: expVar){
            env.applySeq(var.getLhsId(), Effect.RW);
            STentry seqEntry = env.lookupForEffectAnalysis(var.getLhsId().getTextId());

            if(seqEntry.getIVarStatus(var.getLhsId().getTextId()).getType() == Effect.TOP)
                res.add(new  SemanticError("Variable " + var.getLhsId().getTextId() + " is used after deletion."));
        }


        return res;
    }

    @Override
    public List<LhsNode> getExpVar() {
        return exp.getExpVar();
    }
}
