package ast;
import ast.IdNode;

public class LhsNode extends NodeSuper{
    final private IdNode id;
    // LhsNode is just a plain identifier only when lhs == null.
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
        return null;
    }

    @Override
    public NodeInterface typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
