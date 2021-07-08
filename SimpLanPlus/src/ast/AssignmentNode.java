package ast;
import ast.LhsNode;
import ast.ExpNode;

public class AssignmentNode extends NodeSuper{
    final  private LhsNode lhs;
    final private ExpNode exp;

    public AssignmentNode(LhsNode lhs, ExpNode exp){
        this.lhs = lhs;
        this.exp = exp;
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
