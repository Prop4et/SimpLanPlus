package ast.statements;

import java.util.ArrayList;

import java.util.List;

import ast.IdNode;
import ast.Node;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import exceptions.NotDeclaredException;
import ast.types.FunTypeNode;
import exceptions.TypeException;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class CallNode implements Node{
	private final IdNode id;

	private final List<ExpNode> params;//ExpNode
	
	public CallNode(final IdNode id, final List<ExpNode> params) {
		this.id = id;
		this.params=params;
	}
	
	@Override
	public String toPrint(String indent) {
		String s = "(";
		for(ExpNode p : params) {
			s += p.toPrint("") + ", ";
		}
		s=s.substring(0, s.length()-2);
		s += ")";
		return indent + "call: " + id.toPrint("") + s; 
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		//first of all we check that the ID found is a funTypeNode, otherwise it will be a variable or pointer and this element don't allow call
		TypeNode type = id.typeCheck();
		//then check that the actual and formal params have the same type
		if(type instanceof FunTypeNode){
			List<TypeNode> formalParamsTypes = ((FunTypeNode) type).getParams();
			List<TypeNode>  actualParamsTypes = new ArrayList<>();
			List<ExpNode>  actualParams = new ArrayList<>();
			for(ExpNode p: params){
				actualParams.add(p);
				actualParamsTypes.add(p.typeCheck());
			}
			for(int i=0; i <formalParamsTypes.size(); i++ ){
				if (!Node.sametype(actualParamsTypes.get(i), formalParamsTypes.get(i)))
					throw new TypeException("Type Error: actual parameters types don't match with the formal parameters type. Expected: " + formalParamsTypes + " got " + actualParamsTypes + " in "+ id.toPrint("" )+ actualParams);
			}
		}
		return ((FunTypeNode) type).getReturnedValue();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<>();
		//check if the id exists, if not we're using an undeclared identifier
		errors.addAll(id.checkSemantics(env));
		if(!errors.isEmpty())
			return errors;
		//check all the parameters, if there's something wrong with them during the invocation such as int + bool
		for(ExpNode p : params)
			errors.addAll(p.checkSemantics(env));
		//check if the number of actual parameters is equal to the number of formal parameters
		int nFormalParams = -1;
		int nActualParams = params.size();
		try {
			System.out.print(env.lookup(id.getTextId()).getType().toString());
			nFormalParams = ((FunTypeNode) env.lookup(id.getTextId()).getType()).getParams().size();
		} catch (NotDeclaredException e) {
			//should never here cause we checked before
			//TODO maybe change the lookup operation, bringing the exception outside
		};
		if(nActualParams != nFormalParams)
			errors.add(new SemanticError("There's a difference in the number of actual parameters versus the number of formal parameters declared in the function" + id.getTextId()));
		//idk if i'm missing something but this seems fine to me
		return errors;
	}

}
