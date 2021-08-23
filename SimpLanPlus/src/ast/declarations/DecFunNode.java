
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
import exceptions.NotDeclaredException;
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
		  /*                  âˆ‘_0 = [ x 1 âŸ¼ âŠ¥,â€¦,x m âŸ¼ âŠ¥,y 1 âŸ¼ âŠ¥,â€¦,y n âŸ¼ âŠ¥ ]                                               // âˆ‘_0 Ã¨ l'ambiente di partenza che inizializza gli effetti dei parametri
                âˆ‘|_FUN â¦� âˆ‘_0 [ f âŸ¼ âˆ‘_0 â†’ âˆ‘_1 ] âŠ¢ s : âˆ‘| FUN â¦� âˆ‘_1 [ f âŸ¼ âˆ‘_0 â†’ âˆ‘_1 ]                                     //  âˆ‘_1 Ã¨ l'ambiente che contiene gli effetti dei vari identificatori associati ai parametri  formali della funzione dopo l'analisi del corpo
       ----------------------------------------------------------------------------------------------    [Fseq-e]
            ∑ ⊢ f(var T 1 x 1 ,…,var T m x m ,T 1 ' y 1 ,…,T n ' y n ) s: ∑ [f ⟼ ∑_0 → ∑_1]*/

		ArrayList<SemanticError> errors = new ArrayList<>();


		//setting up the effects
		id.getSTentry().setFunNode(this);
		id.getSTentry().initializeStatus(id);		// ∑_0[f ->∑_0 ->∑_1]  environment
		env.addEntry(id.getTextId(), id.getSTentry());

		env.onScopeEntry();

		STentry argEntry;


		for (ArgNode arg: args){							//Initializing  effect inside the symbol table
			arg.getId().getSTentry().setVarStatus(arg.getId().getTextId(),new Effect(Effect.RW)); 		//dev'essere inizializzata a RW o BOT?
			env.addEntry(arg.getId().getTextId(), arg.getId().getSTentry());
		}

		//fine	primo ∑| FUN ⦁ ∑ 0 [ f ⟼ ∑ 0 → ∑ 1 ]



		Environment env1 = new Environment(env);		 // initializing âˆ‘_1 = [ x 1 âŸ¼ âŠ¥,â€¦,x m âŸ¼ âŠ¥,y 1 âŸ¼ âŠ¥,â€¦,y n âŸ¼ âŠ¥ ]
		Environment oldEnv = new Environment(env);
		errors.addAll(body.checkEffects(env1));						 // first iteration of ∑_1
		//env1.printEnv();

		STentry argStatusInBodyEnv ;						//come si gestiscono gli effetti all'interno della dichiarazione di funzione, se settati a bottom i nodi interni daranno errori perchè non si ha un'inizializzazione esplicita nel corpo,
															// vengono inizializzati con lo stato passato nell'invocazione della funzione?
		//not working apparently
		for (ArgNode arg: args) {
			argStatusInBodyEnv = env1.lookupForEffectAnalysis(arg.getId().getTextId());
			//questo però poi deve essere aggiunto all'ambiente no?
			//id.getSTentry().updateArgsStatus(arg.getId().getTextId(), argStatusInBodyEnv.getIVarStatus(arg.getId().getTextId()));
			try {
				env1.lookup(id.getTextId()).updateArgsStatus(arg.getId().getTextId(), argStatusInBodyEnv.getIVarStatus(arg.getId().getTextId()));
			} catch (NotDeclaredException e) {
				e.printStackTrace();
			}
		}

		while(! oldEnv.equals(env1)) {
			oldEnv = env1;
			errors.addAll(body.checkEffects(env1));
		}

		System.out.print("\n Fixed point has been found. result environment ∑_1: \n");
		env1.printEnv();
		env.replace(env1);
		env.onScopeExit();

		return errors;
	}

}





