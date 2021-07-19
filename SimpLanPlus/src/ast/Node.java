package ast;

import java.util.ArrayList;

import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;
/**
 * Interface representing a generic node in the AST
 */
public interface Node {
  
	/**
	 * Node as a string
	 * @param indent characters before the string
	 * @return the string version of the node
	 */
	String toPrint(String indent);
	/**
	 * Effectively checks the types of every nodes
	 * @return the type of the identifier or expression
	 */
	TypeNode typeCheck() throws TypeException;
	/**
	 * Generates the intermediate code
	 * @return a string which is the ASM code
	 */ 
	String codeGeneration();
   /**
	 * Checks if there are any errors in the subtree given the environment
	 * @param env is the environment at the moment of the typechecking
	 * @return list of {@code SemanticError}
	 */
	ArrayList<SemanticError> checkSemantics(Environment env);
	ArrayList<SemanticError> checkEffects(Environment env);
	/**
	 * Checks if a Node is of the same type of another Node
	 * @param n1 first node
	 * @param n2 second node
	 * @return true if match, false otherwise
	 */
	static boolean sametype(TypeNode n1, TypeNode n2) {
		return n1.getClass().equals(n2.getClass());
	}
}  	