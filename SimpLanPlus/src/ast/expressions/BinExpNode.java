package ast.expressions;

import ast.LhsNode;
import ast.Node;
import ast.types.BoolTypeNode;
import ast.types.IntTypeNode;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import exceptions.TypeException;
import main.LabelLib;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
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
        StringBuilder buffer = new StringBuilder();
        LabelLib labelManager = LabelLib.getInstance();

        buffer.append("; BEGIN ").append(this).append("\n");

        buffer.append(leftExp.codeGeneration());
        buffer.append("push $a0 ; push on the stack e1\n");
        buffer.append(rightExp.codeGeneration());
        buffer.append("lw $t1 0($sp) ;$t1 = e1, $a0 = e2\n");
        buffer.append("pop ;pop e1 from the stack\n");

        switch (operator) {
            case "==": {
                String trueBranchLabel = labelManager.freshLabel("equalTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("beq $t1 $a0 ").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 0 ;e1 != e2\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 1 ;e1 == e2\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case "!=": {
                String trueBranchLabel = labelManager.freshLabel("unequalTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("beq $t1 $a0 ").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 1 ;e1 != e2\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 0 ;e1 == e2\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case "*": {
                buffer.append("mult $a0 $t1 $a0\n");
                break;
            }
            case "/": {
                buffer.append("div $a0 $t1 $a0\n");
                break;
            }
            case "+": {
                buffer.append("add $a0 $t1 $a0\n");
                break;
            }
            case "-": {
                buffer.append("sub $a0 $t1 $a0\n");
                break;
            }
            case "<": {
                String equalTrueBranch = labelManager.freshLabel("equalTrueBranch");
                String endEqualCheck = "end" + equalTrueBranch;
                String lesseqTrueBranch = labelManager.freshLabel("lesseqTrueBranch");
                String endLesseqCheck = "end" + lesseqTrueBranch;

                buffer.append("beq $t1 $a0 ").append(equalTrueBranch).append("\n");
                //False branch => e1 != e2
                buffer.append("bleq $t1 $a0 ").append(lesseqTrueBranch).append("\n");
                //InnerFalse branch => e1 > e2
                buffer.append("li $a0 0\n");
                buffer.append("b ").append(endLesseqCheck).append("\n");
                buffer.append(lesseqTrueBranch).append(":\n");
                buffer.append("li $a0 1\n"); // e1 < e2
                buffer.append(endLesseqCheck).append(":\n");
                buffer.append("b ").append(endEqualCheck).append("\n");
                buffer.append(equalTrueBranch).append(":\n");
                buffer.append("li $a0 0\n");
                buffer.append(endEqualCheck).append(":\n");

                break;
            }
            case "<=": {
                String trueBranchLabel = labelManager.freshLabel("lesseqTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("bleq $t1 $a0").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 0\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 1\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case ">": {
                String trueBranchLabel = labelManager.freshLabel("greaterTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("bleq $t1 $a0").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 1\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 0\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case ">=": {
                String equalTrueBranch = labelManager.freshLabel("equalTrueBranch");
                String endEqualCheck = "end" + equalTrueBranch;
                String lesseqTrueBranch = labelManager.freshLabel("lesseqTrueBranch");
                String endLesseqCheck = "end" + lesseqTrueBranch;

                buffer.append("beq $t1 $a0 ").append(equalTrueBranch).append("\n");
                //False branch => e1 != e2
                buffer.append("bleq $t1 $a0 ").append(lesseqTrueBranch).append("\n");
                //InnerFalse branch => e1 > e2
                buffer.append("li $a0 1\n");
                buffer.append("b ").append(endLesseqCheck).append("\n");
                buffer.append(lesseqTrueBranch).append(":\n");
                buffer.append("li $a0 0\n"); // e1 < e2
                buffer.append(endLesseqCheck).append(":\n");
                buffer.append("b ").append(endEqualCheck).append("\n");
                buffer.append(equalTrueBranch).append(":\n");
                buffer.append("li $a0 1\n"); // e1 == e2
                buffer.append(endEqualCheck).append(":\n");

                break;
            }
            case "&&": {
                buffer.append("and $a0 $t1 $a0\n");
                break;
            }
            case "||": {
                buffer.append("or $a0 $t1 $a0\n");
                break;
            }
        }

        buffer.append("; END ").append(this).append("\n");

        return buffer.toString();
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

        List<LhsNode> expVar = getExpVar();

        for (LhsNode var: expVar){
            env.applySeq(var.getLhsId(), Effect.RW);
            STentry seqEntry = env.lookupForEffectAnalysis(var.getLhsId().getTextId());

            if(seqEntry.getIVarStatus(var.getLhsId().getTextId() ).getType() == Effect.TOP)
                res.add(new  SemanticError("Variable " + var.getLhsId().getTextId() + " is used after deletion."));


        }
        return res;
    }


    @Override
    public List<LhsNode> getExpVar() {
        List<LhsNode> var = new ArrayList<>() ;
        var.addAll(leftExp.getExpVar());
        var.addAll(rightExp.getExpVar());


        return var;
    }
}
