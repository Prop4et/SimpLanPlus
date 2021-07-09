package ast;
import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

public class IdNode implements Node {
    private final String id;

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

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
