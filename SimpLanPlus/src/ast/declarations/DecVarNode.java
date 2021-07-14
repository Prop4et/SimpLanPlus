package ast.declarations;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import exceptions.AlreadyDeclaredException;
import exceptions.TypeException;
import semanticAnalysis.Environment;
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
			if (!Node.sametype(type,expType ))
				throw new TypeException("Type Error: " + id + "is of type " + type + "; cannot assign " + exp + " of type " + expType + ".");
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

		if(exp != null) {
			errors.addAll(exp.checkSemantics(env));
		}

		try {
			env.addDec(id.getTextId(), type);

		} catch (AlreadyDeclaredException exception) {
			errors.add(new SemanticError(exception.getMessage()));
		}

		return errors;
	}

}
