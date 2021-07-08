package ast;

public class IdNode extends NodeSuper {
    private final String id;

    public IdNode(final String id) {
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
