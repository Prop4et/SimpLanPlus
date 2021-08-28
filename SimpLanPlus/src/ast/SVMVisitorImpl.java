package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interpreter.Instruction;
import interpreter.lexer.SVMBaseVisitor;
import interpreter.lexer.SVMParser;

public class SVMVisitorImpl extends SVMBaseVisitor<Void>{
	private final List<Instruction> code = new ArrayList<>();
	//private int i = 0; could directly use code.size() instead
	//labelAdd = (endif3, 3)
    private HashMap<String,Integer> labelAdd = new HashMap<String,Integer>();
    //labelRef = (3, endif3)
    private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();
    
    @Override 
    public Void visitAssembly(SVMParser.AssemblyContext ctx) { 
    	visitChildren(ctx);
    	for (Integer refAdd: labelRef.keySet()) {
    		//that's what should happen, but code is Instruction, so i have to build the instruction
            //code.put(refAdd, labelAdd.get(labelRef.get(refAdd)));
            String label = labelRef.get(refAdd);
            
            Instruction instr = code.get(refAdd);
            //instead of directly assigning the address i have to create the instruction with the address of the instruction instead of the label
            if(instr.getInstruction().equals("beq") || instr.getInstruction().equals("bleq")) {
            	code.add(refAdd, new Instruction(instr.getInstruction(), instr.getArg1(), 0, instr.getArg2(), labelAdd.get(label).toString()));
            }else if(instr.getInstruction().equals("b") || instr.getInstruction().equals("jal")) {
            	code.add(refAdd, new Instruction(instr.getInstruction(), labelAdd.get(label).toString(), 0, null, null));
            }
           
        }
    	return null;
    }
    @Override
    public Void visitPush(SVMParser.PushContext ctx) {
    	code.add(new Instruction("push", ctx.REGISTER().getText(), 0, null, null));
    	return null;
    }
    
    @Override
    public Void visitPop(SVMParser.PopContext ctx) {
    	code.add(new Instruction("pop", null, 0, null, null));
    	return null;
    }
    
    @Override
    public Void visitLw(SVMParser.LwContext ctx) {
    	code.add(new Instruction("lw", ctx.out.getText(), Integer.parseInt(ctx.offset.getText()), ctx.in.getText(), null));
    	return null;
    }
    
    @Override 
    public Void visitSw(SVMParser.SwContext ctx) {
    	code.add(new Instruction("sw", ctx.in.getText(), Integer.parseInt(ctx.offset.getText()), ctx.out.getText(), null));
    	return null;
    }
    
    @Override
    public Void visitLi(SVMParser.LiContext ctx) {
    	code.add(new Instruction("li", ctx.out.getText(), 0, ctx.in.getText(), null));
    	return null;
    }
    
    @Override 
    public Void visitMv(SVMParser.MvContext ctx) {
    	code.add(new Instruction("mv", ctx.out.getText(), 0, ctx.in.getText(), null));
    	return null;
    }
    
    @Override 
    public Void visitAdd(SVMParser.AddContext ctx) {
    	code.add(new Instruction("add", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitSub(SVMParser.SubContext ctx) {
    	code.add(new Instruction("sub", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitMul(SVMParser.MulContext ctx) {
    	code.add(new Instruction("mul", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitDiv(SVMParser.DivContext ctx) {
    	code.add(new Instruction("div", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitAddi(SVMParser.AddiContext ctx) {
    	code.add(new Instruction("addi", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitSubi(SVMParser.SubiContext ctx) {
    	code.add(new Instruction("subi", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitMuli(SVMParser.MuliContext ctx) {
    	code.add(new Instruction("muli", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitDivi(SVMParser.DiviContext ctx) {
    	code.add(new Instruction("divi", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override 
    public Void visitAnd(SVMParser.AndContext ctx) {
    	code.add(new Instruction("and", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override
    public Void visitOr(SVMParser.OrContext ctx) {
    	code.add(new Instruction("or", ctx.out.getText(), 0, ctx.in.getText(), ctx.in2.getText()));
    	return null;
    }
    
    @Override
    public Void visitNot(SVMParser.NotContext ctx) {
    	code.add(new Instruction("not", ctx.out.getText(), 0, ctx.in.getText(), null));
    	return null;
    }
    
    @Override 
    public Void visitBeq(SVMParser.BeqContext ctx) {
    	labelRef.put(code.size(), ctx.LABEL().getText());
    	code.add(new Instruction("beq", ctx.in.getText(), 0, ctx.in2.getText(), ctx.LABEL().getText()));
    	return null;
    }
    
    public Void visitBleq(SVMParser.BleqContext ctx) {
    	labelRef.put(code.size(), ctx.LABEL().getText());
    	code.add(new Instruction("bleq", ctx.in.getText(), 0, ctx.in2.getText(), ctx.LABEL().getText()));
    	return null;
    }
    
    @Override 
    public Void visitB(SVMParser.BContext ctx) {
    	labelRef.put(code.size(), ctx.LABEL().getText());
    	code.add(new Instruction("b", ctx.LABEL().getText(), 0, null, null));
    	return null;
    }
    
    @Override
    public Void visitLabel(SVMParser.LabelContext ctx) {
        labelAdd.put(ctx.LABEL().getText(), code.size());
        return null;
    }    
    
    @Override 
    public Void visitJal(SVMParser.JalContext ctx) {
        labelAdd.put(ctx.LABEL().getText(), code.size());
        code.add(new Instruction("jal", ctx.LABEL().getText(), 0, null, null));
    	return null;
    }
    
    @Override 
    public Void visitJr(SVMParser.JrContext ctx) {
    	code.add(new Instruction("jar", ctx.REGISTER().getText(), 0, null, null));
    	return null;
    }
    
    @Override 
    public Void visitDel(SVMParser.DelContext ctx) { 
    	code.add(new Instruction("del", ctx.REGISTER().getText(), 0, null, null));
    	return null;
    }

    @Override 
    public Void visitPrint(SVMParser.PrintContext ctx) {
    	code.add(new Instruction("print", ctx.REGISTER().getText(), 0, null, null));
    	return null;
    }
    
    @Override 
    public Void visitHalt(SVMParser.HaltContext ctx) {
    	code.add(new Instruction("halt", null, 0, null, null));
    	return null;
    }
    
}

