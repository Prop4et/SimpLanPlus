package ast.expressions;

import ast.Node;
import ast.types.TypeNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

public class NewExpNode extends  ExpNode {
    final private TypeNode type;

    public NewExpNode(TypeNode type){
        this.type = type;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "new " + type.toPrint("");    }

    @Override
    public Node typeCheck()  {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Auto-generated method stub
        return null;
    }

}
