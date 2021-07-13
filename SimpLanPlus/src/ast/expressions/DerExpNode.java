package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.types.TypeNode;
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
    public TypeNode typeCheck() {
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

}
