package ast.statements;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import ast.ArgNode;
import ast.IdNode;
import ast.LhsNode;
import ast.Node;
import ast.expressions.DerExpNode;
import ast.expressions.ExpNode;
import ast.types.TypeNode;
import ast.types.FunTypeNode;
import exceptions.TypeException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

public class CallNode implements Node{
	private final IdNode id;

	private final List<ExpNode> params;//ExpNode
	private int currentNl;
	
	public CallNode(final IdNode id, final List<ExpNode> params) {
		this.id = id;
		this.params=params;
	}
	
	@Override
	public String toPrint(String indent) {
		String s = "(";
		if(!  params.isEmpty()) {
			for (ExpNode p : params) {
				s += p.toPrint("") + ", ";
			}
			s = s.substring(0, s.length());        //s.length() -2
		}
		s += ")";
		return indent + "call: " + id.toPrint("") + s; 
	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		//first of all we check that the ID found is a funTypeNode, otherwise it will be a variable or pointer and this element don't allow call
		TypeNode type = id.typeCheck();
		//then check that the actual and formal params have the same type
		if(type instanceof FunTypeNode){
			List<TypeNode> formalParamsTypes = ((FunTypeNode) type).getParamsType();
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
			return ((FunTypeNode) type).getReturnedValue();
		}
		else
			throw new TypeException("Type Error: trying to invoke a non-function identifier:  " +id.toPrint("") + ". ");
	}

	@Override
	public String codeGeneration() {
		//i feel like something's missing
		String ret = "; BEGIN CALLING " + id.getTextId() + "\n";
		ret += "push $fp\n";
		ret += "push $sp\n";
		ret += "mv $cl $sp\n";
		ret += "addi $sp $sp -1\n";
		ret += "lw $al 0($fp)\n";	
		for(int i = 0; i < currentNl - id.getNl(); i++)
			ret += "lw $al 0($al)\n";
		
		ret += "push $al\n";
		for(int i = params.size()-1; i >= 0; i--){
			ret += params.get(i).codeGeneration() + 
					"push $a0 ; pushing " + params.get(i) +"\n";
		}
		ret += "mv $fp $sp\n";
		ret += "addi $fp $fp " + params.size() + "\n";
		ret += "jal " + id.getTextId(); //decfun saves ra firstly
		ret += "; END CALLING " + id.getTextId()+ "\n";
		return ret;
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
		currentNl = env.getNestingLevel();
		int nFormalParams = 0;
		int nActualParams = params.size();

		if(id.getSTentry().getType() instanceof FunTypeNode)
			nFormalParams =  ((FunTypeNode) id.getSTentry().getType()).getParamsType().size();

		if(nActualParams != nFormalParams)
			errors.add(new SemanticError("There's a difference in the number of actual parameters versus the number of formal parameters declared in the function" + id.getTextId()));
		//idk if i'm missing something but this seems fine to me
		return errors;
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<>();
		ArrayList<SemanticError> expEvalErrors = new ArrayList<>();
		//\Gamma |- f : &t1 x .. x &tm x t1' x .. x tn' -> void 			DONE
		//\Sigma(f) = \Sigma_0 -> Sigma_1 ??								DONE
		//\Sigma_1(yi) <= d, 1<=i<=n										DONE
		//\Sigma' = \Sigma [(zi -> \Sigma(zi) seq rw) where zi \in parameters passed as value], \Sigma(zi) = effect status of zi				DONE
		//\Sigma'' = par [ui -> \Sigma(ui) seq \Sigma1(xi)] 1 <= i <= m, where ui are the parameters passed as reference						DONE?
		// ui should be the status outside the function, xi should be the status inside the function
		//-------------------------------------------------------
		//\Sigma |- f(u1, .., um, e1, .., en) : update(\Sigma', \Sigma'')

		expEvalErrors.addAll(id.checkEffects(env));
		List<ExpNode> passedByReferenceParams = new ArrayList<>();
		List<ExpNode> passedByValueParams = new ArrayList<>();

        // Creating the statuses of the variables given as input to the function call.
        // If actual parameters are expressions not instance of DereferenceExpNode, Effect.READ_WRITE is the status given.		
		for(ExpNode p : params) {
			errors.addAll(p.checkEffects(env));
			if (p instanceof DerExpNode)
				passedByReferenceParams.add(p);
			else
				passedByValueParams.add(p);
		}
		
		STentry fun = env.lookupForEffectAnalysis(id.getTextId());
		//getting Sigma1
		HashMap<String, Effect> sigma1 = fun.getFunStatus().get(1);
		//checking	( ∑ 1 (y i ) ≤ d ) 1 ≤ i ≤ n								//all this  can be moved inside decfunNode
		for (int i =0 ;i<fun.getFunNode().getArgs().size(); i++){
			ArgNode arg = fun.getFunNode().getArgs().get(i);
			Effect e = sigma1.get(arg.getId().getTextId());

			List<ArgNode> passedByValueArgs = fun.getFunNode().getPassedByValueParams();

			if( passedByValueArgs.contains(arg) ) {
				if (e.getType() > Effect.DEL)
					errors.add(new SemanticError("Cannot use " + id + "with effect " + e.getType() + "inside a function. "));
			}
		}


		// getting effect of z_i in ∑ while invoking function
		// ∑'= ∑ [( z i ⟼ ∑(z i )⊳rw ) zi ∈ var(e1,…,en) ]
		STentry varInSigma = new STentry(0,0);
		Effect varInSigmaEffect = new Effect();

		Environment sigmaPrimo = new Environment(env);			// TODO: is it useful? bcs is equal to the updated env, so I think I can work directly on env.
		for (ExpNode exp: passedByValueParams){
			//getting exp variable and setting to rw
			for (LhsNode expVar : exp.getExpVar()){		//what happens if  f( x + y, x + y) ?

				//getting ∑(z i )
				varInSigma  = sigmaPrimo.lookupForEffectAnalysis(expVar.getLhsId().getTextId());
				varInSigmaEffect = varInSigma.getIVarStatus(expVar.getLhsId().getTextId());
				varInSigma.setVarStatus(expVar.getLhsId().getTextId(), Effect.seq(varInSigmaEffect, new Effect(Effect.RW)));
			}
		}
		//∑"= ⊗ i ∈ 1..m [ u i ⟼ ∑(u i )⊳ ∑ 1 (x i )]
		Effect formalParamEffect ;
		List<Environment> resultingEnvironment = new ArrayList<>();
		Environment sigmaSecondo = new Environment(env);

		for (int i = 0; i<passedByReferenceParams.size(); i++){
			ExpNode ithParam = passedByReferenceParams.get(i);
			LhsNode actualParam = ithParam.getExpVar().get(0) ;
			//getting formal and actual params effect from the respective environment.
			varInSigma = env.lookupForEffectAnalysis(actualParam.getLhsId().getTextId());
			varInSigmaEffect = varInSigma.getIVarStatus(actualParam.getLhsId().getTextId());
			ArgNode formalParam = fun.getFunNode().getArgs().get(i);
			formalParamEffect = sigma1.get(formalParam.getId().getTextId());

				//creating the environment that will be used for the Sigma'' creation with par, where we will set the actual params effect to the result of ∑(u i )⊳ ∑ 1 (x i )
			Environment newEnv = new Environment();
			newEnv.onScopeEntry();
			STentry tmp = new STentry(varInSigma.getNl(),varInSigma.getOffset(),varInSigma.getType());
			tmp.initializeStatus(actualParam.getLhsId());
			tmp.setVarStatus(actualParam.getLhsId().getTextId() ,Effect.seq(varInSigmaEffect,formalParamEffect));
			newEnv.addEntry(actualParam.getLhsId().getTextId(), tmp);

			resultingEnvironment.add(newEnv);

		}
		//missing par of all env

		if(resultingEnvironment.size()>0) {
			sigmaSecondo = resultingEnvironment.get(0);
			for (int i = 1; i < resultingEnvironment.size(); i++) {
				sigmaSecondo = Environment.par(sigmaSecondo, resultingEnvironment.get(i));
			}
		}
		//System.out.print("Env before calling function: ");
		//env.printEnv();
		Environment updatedEnv = Environment.update(env,sigmaSecondo);
		env.replace(updatedEnv);
		//System.out.print("Env after calling function: ");
		//env.printEnv();

		if(! expEvalErrors.isEmpty()) {
			errors.add(new SemanticError("During invocation you're trying to use bad expression: "));
			errors.addAll(expEvalErrors);
		}
		return errors;
	}

}
