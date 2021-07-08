package ast;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import ast.LhsNode;
import parser.SimpLanPlusParser;
import parser.SimpLanPlusVisitor;

public class SimpLanPlusVisitorImpl implements SimpLanPlusVisitor<NodeInterface> {
    @Override
    public NodeInterface visitBlock(SimpLanPlusParser.BlockContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitAssigtStat(SimpLanPlusParser.AssigtStatContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitDeletStat(SimpLanPlusParser.DeletStatContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitPrintStat(SimpLanPlusParser.PrintStatContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitRetStat(SimpLanPlusParser.RetStatContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitIteStat(SimpLanPlusParser.IteStatContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitCallStat(SimpLanPlusParser.CallStatContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitBlockStat(SimpLanPlusParser.BlockStatContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitDeclarateFun(SimpLanPlusParser.DeclarateFunContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitDeclarateVar(SimpLanPlusParser.DeclarateVarContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitDecFun(SimpLanPlusParser.DecFunContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitType(SimpLanPlusParser.TypeContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitFunType(SimpLanPlusParser.FunTypeContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitArg(SimpLanPlusParser.ArgContext ctx) {
        return null;
    }

    @Override
    public AssignmentNode visitAssignment(SimpLanPlusParser.AssignmentContext ctx) {
        LhsNode lhs = visitLhs(ctx.lhs());
        lhs.setAssignment(true);
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new AssignmentNode(lhs, exp);
    }

    @Override
    public LhsNode visitLhs(SimpLanPlusParser.LhsContext ctx) {
        if (ctx.lhs() == null) {
            // case lhs = ID
            IdNode id = new IdNode(ctx.ID().getText());
            return new LhsNode(id, null);
        } else{
            // case lhs = lhs '^'
            LhsNode lhs = visitLhs(ctx.lhs());
            return new LhsNode(lhs.getLhsId(), lhs);
        }
    }

    @Override
    public DeletionNode visitDeletion(SimpLanPlusParser.DeletionContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText());

        return new DeletionNode(id);
    }

    @Override
    public PrintNode visitPrint(SimpLanPlusParser.PrintContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new PrintNode(exp);
    }

    @Override
    public RetNode visitRet(SimpLanPlusParser.RetContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new RetNode(exp);
    }

    @Override
    public NodeInterface visitIte(SimpLanPlusParser.IteContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitCall(SimpLanPlusParser.CallContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitBinExp(SimpLanPlusParser.BinExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitDerExp(SimpLanPlusParser.DerExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitNewExp(SimpLanPlusParser.NewExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitValExp(SimpLanPlusParser.ValExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitCallExp(SimpLanPlusParser.CallExpContext ctx) {
        return null;
    }

    @Override
    public NodeInterface visitNotExp(SimpLanPlusParser.NotExpContext ctx) {
        return null;
    }


    @Override
    public NodeInterface visit(ParseTree parseTree) {
        return null;
    }

    @Override
    public NodeInterface visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public NodeInterface visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public NodeInterface visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
