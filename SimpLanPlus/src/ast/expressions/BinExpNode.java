package ast.expressions;

import ast.Node;

public class BinExpNode extends ExpNode{
    final private ExpNode leftExp;
    final private ExpNode rightExp;
    final private String operator;

    public  BinExpNode(ExpNode leftExp, String operator, ExpNode rightExp){
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.operator = operator;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
