package ast.statements;

import java.util.ArrayList;

import ast.Node;
import ast.expressions.ExpNode;
import ast.types.BoolTypeNode;
import ast.types.TypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class IteNode implements Node{
	private final ExpNode cond; //ExpNode
	private final StatementNode thenB;//then branch is a statement
	private final StatementNode elseB;//else branch is another statement
	
	public IteNode(final ExpNode cond, final StatementNode thenB, final StatementNode elseB) {
		this.cond = cond;
		this.thenB = thenB;
		this.elseB = elseB;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "if cond: " + cond.toPrint("") + "\n" + indent + indent + "then: " + thenB.toPrint("") + 
				(elseB != null ? "\n"+indent +indent + "else:" + indent +"\n"+ elseB.toPrint(indent+indent) : "");
	}

	@Override
	public TypeNode typeCheck() throws TypeException {

		if(! Node.sametype(cond.typeCheck(), new BoolTypeNode()) )			//can be check that cond is instanceof Bool event in case
			throw new TypeException("Type Error: the if condition " + cond + "is not of type BOOL.");
		TypeNode thenBType = thenB.typeCheck();
		if(elseB != null) {
			if (! Node.sametype(thenBType, elseB.typeCheck()))     //TODO: da rivedere meglio le regole di inferenza dell'if e implementazione sameas
				throw new TypeException("Type Error: Incompatible types in then else branches: then has type " + thenBType + " while else has type " + elseB.typeCheck());
			//otherwise e == t, so I can return any one between the two
		}
		return thenBType; //t

	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//create the result
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//check semantics in the condition
		res.addAll(cond.checkSemantics(env));
		//check semantics in the then and in the else exp
		res.addAll(thenB.checkSemantics(env));
		if(elseB != null)		//the else branch is optional
			res.addAll(elseB.checkSemantics(env));
		//env.printEnv();

		return res;
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		/*
		If inference rule:

		Eps |- condition : Eps'    Eps' |- thenBranch : Eps1    Eps' |- elseBranch : Eps2
         * ---------------------------------------------------------------------------------[If-e]
         *   env |- 'if' '(' condition ')' thenBranch 'else' elseBranch : max(Eps1, Eps2)
		 */
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		res.addAll(cond.checkEffects(env));

		//check effect in the then and in the else exp
		if(elseB != null) {    //the else branch is optional
			Environment thenBranchEnv = new Environment(env);
			res.addAll(thenB.checkEffects(thenBranchEnv));       	 //creating env1

			Environment elseBranchEnv = new Environment(env);		//creating env2
			res.addAll(elseB.checkEffects(elseBranchEnv));

			//getting the max environment between Env1 and Env2
			Environment maxEnv = Environment.max(thenBranchEnv,elseBranchEnv);
			env.replace(maxEnv);
			System.out.print("Printing ite environment");
			env.printEnv();
		}
		else										 	//if there isn't else branch there is no need to calculate max(Eps1,Eps2) so we can work on Eps
			res.addAll(thenB.checkEffects(env));
		return res;
	}

}
