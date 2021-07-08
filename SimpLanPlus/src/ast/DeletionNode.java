package ast;

public class DeletionNode extends NodeSuper {
    final IdNode id;

    public DeletionNode(IdNode id) {
        this.id = id;
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
