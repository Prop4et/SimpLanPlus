package ast;
import ast.IdNode;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

public class LhsNode implements Node{
    final private IdNode id;
    //if lhs == null then lhs is an ID.
    final private LhsNode lhs;
    private boolean isAssignment;

    public LhsNode(IdNode id, LhsNode lhs) {
        this.id = id;
        this.lhs = lhs;
        this.isAssignment = false;
    }
    public IdNode getLhsId(){
        return id;
    }
    public void setAssignment(boolean isAssignment){
        this.isAssignment = isAssignment;
    }
    @Override
    public String toPrint(String indent) {
        if (lhs == null) {
            return indent + id.toPrint("");
        }

        return lhs.toPrint("") + "^";
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

        if (lhs == null) {
            return id.checkSemantics(env);
        }
        return lhs.checkSemantics(env);

    }
}
