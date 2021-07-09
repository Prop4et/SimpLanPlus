package ast;
import java.util.*;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import ast.declarations.DeclarationNode;
import ast.expressions.ExpNode;
import ast.statements.BlockNode;
import ast.statements.AssignmentNode;
import ast.statements.AssigtStatNode;
import ast.statements.BlockStatNode;
import ast.statements.CallStatNode;
import ast.statements.DeleteStatNode;
import ast.statements.DeletionNode;
import ast.statements.IteStatNode;
import ast.statements.PrintStatNode;
import ast.statements.PrintNode;
import ast.statements.RetStatNode;
import ast.statements.RetNode;
import ast.statements.StatementNode;
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
    public Node visitIte(SimpLanPlusParser.IteContext ctx) {
        return null;
    }

    @Override
    public Node visitCall(SimpLanPlusParser.CallContext ctx) {
        return null;
    }

    @Override
    public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        return null;
    }

    @Override
    public Node visitBinExp(SimpLanPlusParser.BinExpContext ctx) {
        return null;
    }

    @Override
    public Node visitDerExp(SimpLanPlusParser.DerExpContext ctx) {
        return null;
    }

    @Override
    public Node visitNewExp(SimpLanPlusParser.NewExpContext ctx) {
        return null;
    }

    @Override
    public Node visitValExp(SimpLanPlusParser.ValExpContext ctx) {
        return null;
    }

    @Override
    public Node visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
        return null;
    }

    @Override
    public Node visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) {
        return null;
    }

    @Override
    public Node visitCallExp(SimpLanPlusParser.CallExpContext ctx) {
        return null;
    }

    @Override
    public Node visitNotExp(SimpLanPlusParser.NotExpContext ctx) {
        return null;
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
