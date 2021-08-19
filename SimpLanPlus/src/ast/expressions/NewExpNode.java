package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.types.PointerTypeNode;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class NewExpNode extends  ExpNode {
    final private TypeNode type;

    public NewExpNode(TypeNode type){
        this.type = type;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "new " + type.toPrint("");    }

    @Override
    public TypeNode typeCheck() throws TypeException {
        return new PointerTypeNode(type);
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
    	//no need to check type, that's another checkSemantics method inside the base type
        return new ArrayList<SemanticError>();
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public List<LhsNode> getExpVar() {
        return new ArrayList<>();
    }
}
