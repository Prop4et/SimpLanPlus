package ast.declarations;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import exceptions.AlreadyDeclaredException;
import exceptions.NotDeclaredException;
import exceptions.TypeException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

public class DecVarNode implements Node{
	private final TypeNode type;
	private final IdNode id;
	private final ExpNode exp;
	
	public DecVarNode(final TypeNode type, final IdNode id, final ExpNode exp) {
		this.type = type;
		this.id = id;
		this.exp = exp;
	}
	
	@Override
	public String toPrint(String indent) {
		String exp = "";
		if(this.exp != null) 
			exp = " = " + this.exp.toPrint(""); 
		return indent + "Var: " + id.toPrint("") + " : " + type.toPrint("") + exp;
 	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		if(exp != null) {
			TypeNode expType = exp.typeCheck();
			if (!Node.sametype(type, expType))
				throw new TypeException("Type Error: Var " + id.getTextId() + " is of type " + type + "; cannot assign " + exp + " of type " + expType + ".");
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<>();
		STentry addedEntry;
		if(exp != null) {
			errors.addAll(exp.checkSemantics(env));
		}
		try {
			addedEntry = env.addDec(id.getTextId(), type);
			id.setSTentry(addedEntry);
		} catch (AlreadyDeclaredException exception) {
			errors.add(new SemanticError(exception.getMessage()));
		}

		return errors;
	}
	@Override
	public ArrayList<SemanticError> checkEffects(Environment env)  {
		ArrayList<SemanticError> errors = new ArrayList<>();

		id.getSTentry().initializeStatus();	//is it ok here? or is it better to initialize the status when adding the var to the symbleTable?
		if (exp != null) {
			id.setStatus(new Effect(Effect.RW),0); //set id status to RW;
			errors.addAll(exp.checkEffects(env));
		}
		env.addEntry(id.getTextId(), id.getSTentry());
		/*System.out.print("checking  " +id.getTextId() );
		if(id.getSTentry().getIVarStatus(0).getType() ==   Effect.BOT)
			System.out.print("BOT"  + "\n");
		else
			System.out.print("RW"  + "\n");*/

		return errors;
	}

}
