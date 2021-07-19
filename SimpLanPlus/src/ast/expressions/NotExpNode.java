package ast.expressions;

import ast.Node;
import ast.types.BoolTypeNode;
import ast.types.IntTypeNode;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

public class NotExpNode extends ExpNode{
    final private ExpNode exp;

    public NotExpNode(ExpNode exp){
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "!" + exp.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeException {
        if( ! (exp.typeCheck() instanceof BoolTypeNode))
            throw new TypeException("Type Error: Bool expression cannot have negative sign.");
        return new BoolTypeNode();
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
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(exp.checkSemantics(env));

        return res;
    }

}
