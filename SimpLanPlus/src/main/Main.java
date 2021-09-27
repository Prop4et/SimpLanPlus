package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import ast.SVMVisitorImpl;
import ast.statements.BlockNode;
import ast.types.TypeNode;
import exceptions.MemoryAccessException;
import exceptions.TypeException;
import interpreter.ExecuteSVM;
import interpreter.lexer.SVMLexer;
import interpreter.lexer.SVMParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
import parser.VerboseListener;
import ast.SimpLanPlusVisitorImpl;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;
import ast.SVMVisitorImpl;

public class Main {

	public static void main(String[] args) throws Exception {

		File path = new File("./Tests/");

		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {        //test all the files in Tests folder
				System.out.print("PROCESSING " + files[i] + ": \n");
				FileInputStream is = new FileInputStream(files[i]);
				ANTLRInputStream input = new ANTLRInputStream(is);
				SimpLanPlusLexer lexer = new SimpLanPlusLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				lexer.removeErrorListeners();
				lexer.addErrorListener(new VerboseListener());

				if (lexer.errorCount() > 0) {
					System.out.println("Lexical errors in the file. Exiting the compilation process now");
				} else {
					SimpLanPlusParser parser = new SimpLanPlusParser(tokens);
					parser.removeErrorListeners();
					parser.addErrorListener(new VerboseListener());
					SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();
					BlockNode ast = visitor.visitBlock(parser.block()); //generazione AST
					ast.setMain(true); //i need this for code generation
					//SIMPLE CHECK FOR LEXER ERRORS
					if (parser.getNumberOfSyntaxErrors() > 0) {
						System.out.println("The program was not in the right format. Exiting the compilation process now");
					} else {
						Environment env = new Environment();
						ArrayList<SemanticError> SemanticErr = ast.checkSemantics(env);
						if (!SemanticErr.isEmpty()) {
							if (SemanticErr.size() == 1)
								System.out.println("You had: " + SemanticErr.size() + " error: ");
							else
								System.out.println("You had: " + SemanticErr.size() + " errors:");
							for (SemanticError e : SemanticErr)
								System.out.println("\t" + e);
						}
						else {
							//System.out.println("Visualizing AST...");
							//System.out.println(ast.toPrint(""));
							try {
								TypeNode type = ast.typeCheck(); //type-checking bottom-up
								System.out.println(type.toPrint("Type checking ok! Type of the program is: "));
							} catch (TypeException e) {
								System.out.println(e.getMessage());
								continue;
							}
							ArrayList<SemanticError> effectsErr = ast.checkEffects(env);
							List<String> unique = new ArrayList<>();
							if (!effectsErr.isEmpty()) {
								System.out.println("Effects analysis:");
								for (SemanticError e : effectsErr)
									if (!unique.contains(e.msg))
										unique.add(e.msg);
								for (String s : unique)
									System.out.println("\t" + s);
							} else {
								File dir = new File("Tests/compiledTests/");
								if (!dir.exists()) {
									dir.mkdirs();
								}
								String code = ast.codeGeneration();
								String tmp = files[i].getPath().substring(8);
								BufferedWriter out = new BufferedWriter(new FileWriter("Tests/compiledTests/" + tmp + ".asm"));
								out.write(code);
								out.close();
								System.out.println("Code generated! Assembling and running generated code.");

								FileInputStream isASM = new FileInputStream("Tests/compiledTests/" + tmp + ".asm");
								ANTLRInputStream inputASM = new ANTLRInputStream(isASM);
								SVMLexer lexerASM = new SVMLexer(inputASM);
								CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
								SVMParser parserASM = new SVMParser(tokensASM);

								//parserASM.assembly();

								SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
								visitorSVM.visit(parserASM.assembly());

								System.out.println("You had: " + lexerASM.errorCount() + " lexical errors and " + parserASM.getNumberOfSyntaxErrors() + " syntax errors.");
								if (lexerASM.errorCount() > 0 || parserASM.getNumberOfSyntaxErrors() > 0) System.exit(1);

								System.out.println("Starting Virtual Machine...");

								ExecuteSVM vm = new ExecuteSVM(30, visitorSVM.getCode());
								try {
									vm.run();
								} catch (MemoryAccessException e) {
									System.out.println(e.getMessage());
								}
							}
						}
					}
				}
			}
		}
	}
}