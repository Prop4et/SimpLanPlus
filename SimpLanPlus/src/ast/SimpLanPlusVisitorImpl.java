package ast;
import java.util.*;

import ast.expressions.*;
import ast.statements.*;
import ast.types.BoolNode;
import ast.types.TypeNode;
import ast.types.IntNode;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import ast.declarations.DeclarationNode;
import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node>{
	public BlockNode visitBlock(SimpLanPlusParser.BlockContext ctx) {
		List<DeclarationNode> decs = new ArrayList<>();
		List<StatementNode> stms = new ArrayList<>();
		
		for (SimpLanPlusParser.DeclarationContext dc : ctx.declaration()){
			decs.add((DeclarationNode) visit(dc));
		}
		
		for(SimpLanPlusParser.StatementContext sc : ctx.statement()) {
			stms.add((StatementNode) visit(sc));
		}
		
		return new BlockNode(decs, stms);
	}
	
	public AssigtStatNode visitAssigtStat(SimpLanPlusParser.AssigtStatContext ctx) {
		return new AssigtStatNode(visitAssignment(ctx.assignment()));
	}
	
	public DeleteStatNode visitDeletionStat(SimpLanPlusParser.DeleteStatContext ctx) {
		return new DeleteStatNode(visitDeletion(ctx.deletion()));
	}
	
	public PrintStatNode visitPrintStat(SimpLanPlusParser.PrintStatContext ctx) {
		return new PrintStatNode(visitPrint(ctx.print()));
	}
	
	public RetStatNode visitRetStat(SimpLanPlusParser.RetStatContext ctx) {
		return new RetStatNode(visitRet(ctx.ret()));
	}
	
	public IteStatNode visitIteStat(SimpLanPlusParser.IteStatContext ctx) {
		return new IteStatNode(visitIte(ctx.ite()));
	}

    @Override
    public CallStatNode visitCallStat(SimpLanPlusParser.CallStatContext ctx) {
    	return new CallStatNode(visitCall(ctx.call()));
    }

    @Override
    public BlockStatNode visitBlockStat(SimpLanPlusParser.BlockStatContext ctx) {
        return new BlockStatNode(visitBlock(ctx.block()));
    }

    @Override
    public Node visitDeclarateFun(SimpLanPlusParser.DeclarateFunContext ctx) {
        return null;
    }

    @Override
    public Node visitDeclarateVar(SimpLanPlusParser.DeclarateVarContext ctx) {
        return null;
    }

    @Override
    public Node visitDecFun(SimpLanPlusParser.DecFunContext ctx) {
        return null;
    }

    @Override
    public Node visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
        return null;
    }

    @Override
    public Node visitType(SimpLanPlusParser.TypeContext ctx) {
        return null;
    }

    @Override
    public Node visitFunType(SimpLanPlusParser.FunTypeContext ctx) {
        return null;
    }

    @Override
    public Node visitArg(SimpLanPlusParser.ArgContext ctx) {
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
    public IteNode visitIte(SimpLanPlusParser.IteContext ctx) {
        ExpNode condition = (ExpNode) visit(ctx.condition);
        StatementNode thenStatement = (StatementNode) visit(ctx.thenBranch);
        StatementNode elseStatement = (StatementNode) visit(ctx.elseBranch);

        return new IteNode(condition, thenStatement, elseStatement);
    }

    @Override
    public CallNode visitCall(SimpLanPlusParser.CallContext ctx) {

            IdNode id = new IdNode(ctx.ID().getText());
            List<ExpNode> parameters = new ArrayList<>();

            for (SimpLanPlusParser.ExpContext expContext : ctx.exp()) {     //// ctx.exp() return a list of ExpContex, so each context correspond to a parameter
                parameters.add((ExpNode) visit(expContext));
            }

            return new CallNode(id, parameters);
    }

    @Override
    public BaseExpNode visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new BaseExpNode(exp);
    }

    @Override
    public BinExpNode visitBinExp(SimpLanPlusParser.BinExpContext ctx) {
        ExpNode leftExp = (ExpNode) visit(ctx.left);
        ExpNode rightExp = (ExpNode) visit(ctx.right);
        String operator = ctx.op.getText();

        return new BinExpNode(leftExp, operator, rightExp);
    }

    @Override
    public DerExpNode visitDerExp(SimpLanPlusParser.DerExpContext ctx) {
        LhsNode lhs = visitLhs(ctx.lhs());

        return new DerExpNode(lhs); //da vedere
    }

    @Override
    public NewExpNode visitNewExp(SimpLanPlusParser.NewExpContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.type());

        return new NewExpNode(type);
    }

    @Override
    public IntNode visitValExp(SimpLanPlusParser.ValExpContext ctx) {
        int val = Integer.parseInt(ctx.NUMBER().getText());
        return new IntNode(val);
    }

    @Override
    public NegExpNode visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new NegExpNode(exp);
    }



    @Override
    public BoolNode visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) {
        Boolean type = Boolean.parseBoolean(ctx.BOOL().getText());

        return new BoolNode(type);
    }

    @Override
    public CallExpNode visitCallExp(SimpLanPlusParser.CallExpContext ctx) {
        CallNode callNode = visitCall(ctx.call());

        return new CallExpNode(callNode);
    }

    @Override
    public Node visitNotExp(SimpLanPlusParser.NotExpContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new NotExpNode(exp);
    }

 /*
    @Override
    public Node visit(ParseTree parseTree) {
        return null;
    }
*/
    @Override
    public Node visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Node visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Node visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
