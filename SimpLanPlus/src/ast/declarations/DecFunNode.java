
package ast.declarations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ast.ArgNode;
import ast.IdNode;
import ast.Node;
import ast.statements.BlockNode;
import ast.types.FunTypeNode;
import ast.types.PointerTypeNode;
import ast.types.TypeNode;
import exceptions.AlreadyDeclaredException;
import exceptions.TypeException;
import semanticAnalysis.Effect;
import semanticAnalysis.Environment;
import semanticAnalysis.STentry;
import semanticAnalysis.SemanticError;

public class DecFunNode implements Node{

	private final TypeNode type;
	private final IdNode id;
	private final List<ArgNode> args;
	private final BlockNode body;
	private final FunTypeNode typeFun;



	public DecFunNode(final TypeNode type, final IdNode id, final List<ArgNode> args, BlockNode body) {
		this.type = type;
		this.id = id;
		this.args = args;
		this.body = body;
		List<TypeNode> argsType = args.stream().map(ArgNode::getType).collect(Collectors.toList());
		typeFun = new FunTypeNode(argsType, type);
	}

	public List<ArgNode> getArgs(){return this.args;}

	@Override
	public String toPrint(String indent) {

		String dec = indent+"Function: " + id.toPrint("") + " : " +
				args.stream().map(arg -> "(" + arg.toPrint("") +")").reduce("",(subtotal, element) -> subtotal + "" + element + " x ");
		dec=dec.substring(0, dec.length()-2);
		dec += "-> " +type.toPrint("");
		return dec + "\n" + body.toPrint(indent);

	}

	@Override
	public TypeNode typeCheck() throws TypeException {
		//System.out.print(body);
		if (type instanceof PointerTypeNode)
			throw new TypeException("Functions are not allowed to return pointers");
		if (!Node.sametype(type, body.typeCheck()))
			throw new TypeException("Type Error: Return type and function type are incompatible");
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	//should be fine for what concerns the checksemantics
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<>();
		STentry argEntry, functionEntry;

		try{
			//add function name to the environment
			env.addDec(id.getTextId(), typeFun);
			//create the new block
			env.onScopeEntry();
			//add the function to the scope for the arguments in case of (non mutual) recursion
			functionEntry = env.addDec(id.getTextId(), typeFun);
			id.setSTentry(functionEntry);

			//add the arguments to the new scope created
			//TODO is it right to declare new variables inside the function with the same name of the parameters?
			//if not, when body gets evaluated there shouldn't be a new scope creation, that's what happens
			for(ArgNode arg : args) {
				argEntry = env.addDec(arg.getId().getTextId(), arg.getType());
				arg.getId().setSTentry(argEntry);
			}
			//body evaluation in which yet another scope is created, should we avoid this? DONE
			body.setNewScope(false);
			//tbh here the environment in which we evaluate the body should be just the top one
			//this thing is sick tho, what's happening here is that i created a whole new environment with just the scope of the function
			//i know it's gonna be a pain later on probably, thinking about effects and stuff like that
			errors.addAll(body.checkSemantics(env));

			env.onScopeExit();

		}catch(AlreadyDeclaredException e){
			errors.add(new SemanticError(e.getMessage()));
		}
		return errors;
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		  /*                  ∑_0 = [ x 1 ⟼ ⊥,…,x m ⟼ ⊥,y 1 ⟼ ⊥,…,y n ⟼ ⊥ ]                                               // ∑_0 è l'ambiente di partenza che inizializza gli effetti dei parametri
                ∑|_FUN ⦁ ∑_0 [ f ⟼ ∑_0 → ∑_1 ] ⊢ s : ∑| FUN ⦁ ∑_1 [ f ⟼ ∑_0 → ∑_1 ]                                     //  ∑_1 è l'ambiente che contiene gli effetti dei vari identificatori associati ai parametri  formali della funzione dopo l'analisi del corpo
       ----------------------------------------------------------------------------------------------    [Fseq-e]
            ∑ ⊢ f(var T 1 x 1 ,…,var T m x m ,T 1 ' y 1 ,…,T n ' y n ) s: ∑ [f ⟼ ∑_0 → ∑_1]*/
		//setting up the effects
		id.getSTentry().setFunNode(this);
		id.getSTentry().initializeStatus(id);		// ∑_0[f ->∑_0 ]  environment
		env.addEntry(id.getTextId(), id.getSTentry());
		env.printEnv();
		List<HashMap<String, Effect>> s1  = new ArrayList<>();
		s1.addAll(id.getSTentry().getFunStatus()); // ∑_1  environment

		System.out.print("printing s1 " + s1);

		env.onScopeEntry();

		STentry argEntry;

		for (ArgNode arg: args){			//Initializing  ∑_0  environment
			arg.getId().getSTentry().initializeStatus(arg.getId());
			env.addEntry(arg.getId().getTextId(), arg.getId().getSTentry());
		}
		System.out.print("adding args to the env:");
		env.printEnv();									//env = g  , ^int, int -> void 0 1 0, 0, ->0,
																	//x ^int -4 1 0
																	//y int -8 1 0
	//fine	primo ∑| FUN ⦁ ∑ 0 [ f ⟼ ∑ 0 → ∑ 1 ]



		Environment env1 = new Environment(env);		 // initializing ∑_1 = [ x 1 ⟼ ⊥,…,x m ⟼ ⊥,y 1 ⟼ ⊥,…,y n ⟼ ⊥ ]
		Environment oldEnv = new Environment(env);
		body.checkEffects(env1);						 // first iteration of ∑_1
		System.out.print("env1: ");
		env1.printEnv();

		for (int argIndex = 0; argIndex < args.size(); argIndex++) {
			argEntry = env1.lookupForEffectAnalysis(args.get(argIndex).getId().getTextId());
			//STentry getEnvEntry = env1.lookupForEffectAnalysis(args.get(argIndex).getId().getTextId());

			if(argEntry.getType() instanceof PointerTypeNode) {
				int numberOfDereference = argEntry.getType().getDereferenceLevel() - 1;
				for (int i = numberOfDereference; i >= 0; i--) {
					System.out.print(argEntry.getType().toPrint("") + argEntry.getIVarStatus(id.getTextId()).getType());
					id.getSTentry().setArgsStatus(argIndex, argEntry.getIVarStatus(id.getTextId()), 0);
				}
			}
			else{
				System.out.print(argEntry.getType().toPrint("") + argEntry.getIVarStatus(id.getTextId()).getType());
				id.getSTentry().setArgsStatus(argIndex, argEntry.getIVarStatus(id.getTextId()), 0);

			}
		}

	//	id.getSTentry().setArgsStatus();


		env1.printEnv();

		//id.setStatus();
		while(! oldEnv.equals(env1)) {
			body.checkEffects(env1);
		}


		System.out.print("\n ∑_1: \n");
		env.printEnv();

		return new ArrayList<>();

	}

}





