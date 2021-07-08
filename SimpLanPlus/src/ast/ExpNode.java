package ast;

public class ExpNode extends NodeSuper{
    final private ExpNode exp;
    public ExpNode(ExpNode exp){
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
