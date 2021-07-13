package ast.expressions;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

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
        return  exp.typeCheck();
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

}
