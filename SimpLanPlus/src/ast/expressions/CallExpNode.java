package ast.expressions;

import ast.Node;
import ast.statements.CallNode;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

public class CallExpNode extends ExpNode {
    final private CallNode call;
    public CallExpNode(CallNode call) {
        this.call = call;
    }
    @Override
    public String toPrint(String indent) {
        return indent + call.toPrint(indent);
    }
    public TypeNode typeCheck()  {
        return null;
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
}
