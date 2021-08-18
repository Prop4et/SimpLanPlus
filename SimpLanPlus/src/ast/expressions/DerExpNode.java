package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

public class DerExpNode extends ExpNode{
    final private LhsNode lhs;

    public DerExpNode(LhsNode lhs){
        this.lhs = lhs;
    }

    @Override
    public String toPrint(String indent) {
        return indent + lhs.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeException {
       return lhs.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(lhs.checkSemantics(env));

        return res;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(lhs.checkEffects(env));
        // we are processing a dereferentiation Node 	lhs -> lhs^ -> id^
        if (lhs.getLhsId().getSTentry().getVarStatus().equals(Effect.BOT)) {
            res.add(new SemanticError(lhs + " is used prior to initialization."));
        }

        return res;
    }

}
