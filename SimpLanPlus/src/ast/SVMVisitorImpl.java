package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interpreter.Instruction;
import interpreter.lexer.SVMBaseVisitor;
import interpreter.lexer.SVMParser;

public class SVMVisitorImpl extends SVMBaseVisitor<Void>{
	private final List<Instruction> code = new ArrayList<>();
	private int i = 0;
    private HashMap<String,Integer> labelAdd = new HashMap<String,Integer>();
    private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();
    
    @Override 
    public Void visitAssembly(SVMParser.AssemblyContext ctx) { 
    	visitChildren(ctx);
    	for (Integer refAdd: labelRef.keySet()) {
            
    	}
    	return null;
    }
}
