package parser;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.*;

public class VerboseListener extends BaseErrorListener {

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
		List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack); 
        
        System.out.println("Program format: "+ stack);
        System.out.println("Line " + line + ":" + charPositionInLine + " at " + offendingSymbol.toString()+": "+msg);
        
        printError(recognizer, line);

	}
	/*prints the line with the error*/
	private void printError(Recognizer<?, ?> recognizer, int line) {
        CommonTokenStream tokens = (CommonTokenStream) recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] inputLines = input.split("\n");
        System.out.println("\nError: " + inputLines[line-1]);
    }
	
	
}