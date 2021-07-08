package ast;
import ast.ExpNode;

public class PrintNode extends NodeSuper {
    final private ExpNode exp;

    public PrintNode(ExpNode exp){
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
