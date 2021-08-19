package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.statements.CallNode;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class CallExpNode extends ExpNode {
    final private CallNode call;
    public CallExpNode(CallNode call) {
        this.call = call;
    }
    @Override
    public String toPrint(String indent) {
        return indent + call.toPrint(indent);
    }
    public TypeNode typeCheck() throws TypeException {
        return call.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(call.checkSemantics(env));
        return res;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(call.checkEffects(env));
        return res;
    }

    @Override
    public List<LhsNode> getExpVar() {
        return null;
    }
}
