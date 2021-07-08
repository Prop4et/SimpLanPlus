package ast;

import java.util.*;

import ast.declarations.DeclarationNode;
import ast.prog.BlockNode;
import ast.statements.AssigtNode;
import ast.statements.AssigtStatNode;
import ast.statements.DeleteStatNode;
import ast.statements.DeletionNode;
import ast.statements.IteStatNode;
import ast.statements.PrintStatNode;
import ast.statements.RetStatNode;
import ast.statements.StatementNode;
import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser.DeclarationContext;
import parser.SimpLanPlusParser.DeleteStatContext;
import parser.SimpLanPlusParser.StatementContext;
import parser.SimpLanPlusParser.BlockContext;
import parser.SimpLanPlusParser.CallStatContext;
import parser.SimpLanPlusParser.AssigtStatContext;
import parser.SimpLanPlusParser.DeletionContext;
import parser.SimpLanPlusParser.PrintStatContext;
import parser.SimpLanPlusParser.RetStatContext;
import parser.SimpLanPlusParser.IteStatContext;
import parser.SimpLanPlusParser.BlockStatContext;;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node>{
	public BlockNode visitBlock(BlockContext ctx) {
		List<DeclarationNode> decs = new ArrayList<>();
		List<StatementNode> stms = new ArrayList<>();
		
		for (DeclarationContext dc : ctx.declaration()){
			decs.add((DeclarationNode) visit(dc));
		}
		
		for(StatementContext sc : ctx.statement()) {
			stms.add((StatementNode) visit(sc));
		}
		
		return new BlockNode(decs, stms);
	}
	
	public AssigtStatNode visitAssigtStat(AssigtStatContext ctx) {
		return new AssigtStatNode(visitAssignment(ctx.assignment()));
	}
	
	public DeleteStatNode visitDeletionStat(DeleteStatContext ctx) {
		return new DeleteStatNode(visitDeletion(ctx.deletion()));
	}
	
	public PrintStatNode visitPrintStat(PrintStatContext ctx) {
		return new PrintStatNode(visitPrint(ctx.print()));
	}
	
	public RetStatNode visitRetStat(RetStatContext ctx) {
		return new RetStatNode(visitRet(ctx.ret()));
	}
	
	public IteStatNode visitIteStat(IteStatContext ctx) {
		return new IteStatNode(visitIte(ctx.ite()));
	}
	
}
