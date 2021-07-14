package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ast.statements.BlockNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
/*import parser.SVMLexer;
import parser.SVMParser;
import util.Environment;
import util.SemanticError;*/
import ast.SimpLanPlusVisitorImpl;
import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;
//import ast.SVMVisitorImpl;*/

public class Main {
	
	public static void main(String[] args) throws Exception {

		//String fileName = "./SimpLanPlus/Tests/Test5.txt";
		//String fileName = "../../examples/example4.simplan";
		String fileName = "Tests/test9.txt";
		FileInputStream is = new FileInputStream(fileName);
		ANTLRInputStream input = new ANTLRInputStream(is);
		SimpLanPlusLexer lexer = new SimpLanPlusLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		SimpLanPlusParser parser = new SimpLanPlusParser(tokens);
		SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();
		BlockNode ast = visitor.visitBlock(parser.block()); //generazione AST
		//SIMPLE CHECK FOR LEXER ERRORS
		if (lexer.errorCount() > 0){
			System.out.println("The program was not in the right format. Exiting the compilation process now");
			
		} else {
			Environment env = new Environment();
			ArrayList<SemanticError> err = ast.checkSemantics(env);
			if(!err.isEmpty()){
				if(err.size() == 1)
					System.out.println("You had: " +err.size()+" error: ");
				else
					System.out.println("You had: " +err.size()+" errors:");
				for(SemanticError e : err)
					System.out.println("\t" + e);
			} else {
				System.out.println("Visualizing AST...");
				System.out.println(ast.toPrint(""));
			/*
				//Node type = ast.typeCheck(); //type-checking bottom-up
				//System.out.println(type.toPrint("Type checking ok! Type of the program is: "));

				// CODE GENERATION  prova.SimpLanPlus.asm
				String code=ast.codeGeneration(); 
				BufferedWriter out = new BufferedWriter(new FileWriter(fileName+".asm")); 
				out.write(code);
				out.close(); 
				System.out.println("Code generated! Assembling and running generated code.");

				FileInputStream isASM = new FileInputStream(fileName+".asm");
				ANTLRInputStream inputASM = new ANTLRInputStream(isASM);
				SVMLexer lexerASM = new SVMLexer(inputASM);
				CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
				SVMParser parserASM = new SVMParser(tokensASM);

				//parserASM.assembly();

				SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
				visitorSVM.visit(parserASM.assembly()); 

				System.out.println("You had: "+lexerASM.lexicalErrors+" lexical errors and "+parserASM.getNumberOfSyntaxErrors()+" syntax errors.");
				if (lexerASM.lexicalErrors>0 || parserASM.getNumberOfSyntaxErrors()>0) System.exit(1);

				System.out.println("Starting Virtual Machine...");
				ExecuteVM vm = new ExecuteVM(visitorSVM.code);
				vm.cpu();*/
			}
		}


	}

}
