
package ast.declarations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ast.ArgNode;
import ast.IdNode;
import ast.Node;
import ast.expressions.DerExpNode;
import ast.expressions.ExpNode;
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
		this.body.setFunction(true);
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
		
		String labelFun = id.getTextId();
		String ret = "; BEGIN DEFINITION OF " + labelFun + ":\n";
		ret += labelFun + ":\n";
		ret += "sw $ra -1($cl)\n";
		body.setFunEndLabel("end"+labelFun);
		ret += body.codeGeneration();//this code generation should be done in another stable, that is stablee in the example
		ret += "end"+labelFun + ":\n";
		ret += "lw $ra -1($cl)\n";
		ret += "lw $fp 1($cl)\n";
		
		ret += "lw $sp 0($cl) \n";
        ret += "addi $cl $fp 2\n";
		ret += "jr $ra\n";
		ret += ";END DEFINITION OF " + labelFun + "\n";

		return ret;
	}
	
	//should be fine for what concerns the checksemantics
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env1) {
		ArrayList<SemanticError> errors = new ArrayList<>();
		STentry argEntry, functionEntry;

		try{
			//add the function to the scope for the arguments in case of (non mutual) recursion
			functionEntry = env1.addDec(id.getTextId(), typeFun);
			env1.onScopeEntry();
			id.setSTentry(functionEntry);
			Environment env = new Environment(id.getTextId(), functionEntry);
			//create the new block
			env.onScopeEntry();
			//add the arguments to the new scope created
			//TODO is it right to declare new variables inside the function with the same name of the parameters?
			//if not, when body gets evaluated there shouldn't be a new scope creation, that's what happens
			for(ArgNode arg : args) {
				argEntry = env.addDec(arg.getId().getTextId(), arg.getType());
				arg.getId().setSTentry(argEntry);
			}
			//add function name to the environment
			env.addDec(id.getTextId(), typeFun);
			//body evaluation in which yet another scope is created, should we avoid this? DONE
			body.setNewScope(false);
			body.setFunction(true);
			//when evaluating it finds global variables too, but i don't want it to
			
			errors.addAll(body.checkSemantics(env));

			env.onScopeExit();
			env1.onScopeExit();
			

		}catch(AlreadyDeclaredException e){
			errors.add(new SemanticError(e.getMessage()));
		}
		return errors;
	}
	public List<ArgNode> getPassedByReferenceParams(){
		List<ArgNode> passedByReferenceArgs = new ArrayList<>();

		for(ArgNode p : this.args) {
			if (p.getType() instanceof PointerTypeNode)
				passedByReferenceArgs.add(p);
		}
		return passedByReferenceArgs;
	}
	public List<ArgNode> getPassedByValueParams(){
		List<ArgNode> passedByValueArgs = new ArrayList<>();

		for(ArgNode p : this.args) {
			if (p.getType() instanceof PointerTypeNode)
				passedByValueArgs.add(p);
		}
		return passedByValueArgs;
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		/*				        ∑ 0 = [ x 1 ⟼ ⊥,…,x m ⟼ ⊥,y 1 ⟼ ⊥,…,y n ⟼ ⊥ ]
						∑| FUN ⦁ ∑ 0 [ f ⟼ ∑ 0 → ∑ 1 ] ⊢ s : ∑| FUN ⦁ ∑ 1 [ f ⟼ ∑ 0 → ∑ 1 ]
				-----------------------------------------------------------------------------------[Fseq-e]
				∑ ⊢ f(var T 1 x 1 ,…,var T m x m ,T 1 ' y 1 ,…,T n ' y n ) s: ∑ [f ⟼ ∑ 0 → ∑ 1]						 */


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

		STentry argStatusInBodyEnv ;
		//updating fun
		for (ArgNode arg: args) {
			argStatusInBodyEnv = env1.lookupForEffectAnalysis(arg.getId().getTextId());
			env1.lookupForEffectAnalysis(id.getTextId()).updateArgsStatus(arg.getId().getTextId(), argStatusInBodyEnv.getIVarStatus(arg.getId().getTextId()));
			//re-setting args effect to RW for the next evaluation of the body effect.
			env1.lookupForEffectAnalysis(arg.getId().getTextId()).setVarStatus(arg.getId().getTextId(), new Effect(Effect.RW));
		}

		while(!oldEnv.equals(env1)) {
			oldEnv=env1;
			errors.addAll(body.checkEffects(env1));
			//updating arg
			for (ArgNode arg: args) {
				argStatusInBodyEnv = env1.lookupForEffectAnalysis(arg.getId().getTextId());
				env1.lookupForEffectAnalysis(id.getTextId()).updateArgsStatus(arg.getId().getTextId(), argStatusInBodyEnv.getIVarStatus(arg.getId().getTextId()));

			}
		}

	//	System.out.print("\n Fixed point has been found. result environment ∑_1: \n");
		//env1.printEnv();
		env.replace(env1);
		env.onScopeExit();

		return errors;
	}

}





