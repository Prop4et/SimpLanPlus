package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.types.BoolTypeNode;
import ast.types.IntTypeNode;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

import java.util.ArrayList;
import java.util.List;

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
            throw new TypeException("Type Error: unsupported operand type(s) for the operator" + operator + ": " +leftExpType + " and "+ rightExpType);

        switch (operator){
            case "*": case "/": case"+": case "-":        //we need both terms to be int, results will be int
                if( leftExpType instanceof BoolTypeNode || rightExpType instanceof BoolTypeNode)
                    throw new TypeException(" Type Error: impossible to sum bool expression ");
                else
                    return new IntTypeNode();
            case "<": case "<=": case">": case ">=":        //we need both terms to be int, results will be bool
                if( leftExpType instanceof BoolTypeNode || rightExpType instanceof BoolTypeNode)
                    throw new TypeException(" Type Error: impossible to compare bool expression ");
                else
                    return new BoolTypeNode();
            case "==": case "!=":                            //terms can be either int or bool, results will be int
                    return new BoolTypeNode();
            case "&&": case "||":                           //we need both terms to be bool, results will be bool
                if( leftExpType instanceof BoolTypeNode && rightExpType instanceof BoolTypeNode)
                    return new BoolTypeNode();
                else
                    throw new TypeException(" Type Error: expressions are not of type bool ");
        }
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

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(leftExp.checkEffects(env));
        res.addAll(rightExp.checkEffects(env));

        return res;
    }


    @Override
    public List<LhsNode> getExpVar() {
        return null;
    }
}
