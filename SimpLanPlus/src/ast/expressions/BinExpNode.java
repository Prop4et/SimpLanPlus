package ast.expressions;

import ast.Node;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;

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
        return indent + leftExp.toPrint("") + " " + operator + " " + rightExp.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeException {
        TypeNode leftExpType = leftExp.typeCheck();
        TypeNode rightExpType = rightExp.typeCheck();

        if(!Node.sametype(leftExpType, rightExpType))
            throw new TypeException("Type Error: unsupported operand type(s) for" + operator + ":" +leftExpType + " and "+ rightExpType);

        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(leftExp.checkSemantics(env));
        res.addAll(rightExp.checkSemantics(env));

        return res;
    }


}
