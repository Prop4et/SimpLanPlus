package interpreter;

import exceptions.MemoryAccessException;

import java.util.HashMap;
import java.util.List;

public class ExecuteSVM {
	private final int memSize; // Max size of the memory (heap + stack)

    private final List<Instruction> code;
    private final int[] memory;

    
    private int ip = 0;
    private HashMap<String, Integer> registers;
 
    
    public ExecuteSVM(int memSize, List<Instruction> code) {
    	this.memSize = memSize;
        this.code = code;
        
        memory = new int[memSize];

        registers = new HashMap<>();
        registers.put("$sp", memSize);
        registers.put("$fp", memSize -1);
        registers.put("$hp", 0);
        registers.put("$ra", null);
        registers.put("$al", null);
        registers.put("$a0", null);
        registers.put("$t1", null);
        
        }
    
    public void run() throws MemoryAccessException {
    	while(true) {
    		if(registers.get("$hp")+1>=registers.get("$sp")) {
        		throw  new MemoryAccessException("Error: Out of memory");
        	}else {
        		Instruction bytecode = code.get(ip); // fetch
				//System.out.print("getting instr: "+ code.get(ip).getInstruction() +" " + code.get(ip).getArg1() +" " + code.get(ip).getArg2() +" " + code.get(ip).getArg3() +"\n " );
        		ip++;
        		String arg1 = bytecode.getArg1();
                String arg2 = bytecode.getArg2();
                String arg3 = bytecode.getArg3();
                int offset = bytecode.getOffset();
                switch(bytecode.getInstruction()) {
                case "push":
                	registers.put("$sp", registers.get("$sp") - 1); 
                	memory[registers.get("$sp")] = registers.get(arg1);
                	break;
                case "pop":
                	registers.put("$sp", registers.get("$sp") + 1); 
                	break;
                case "lw":
                	//pop the value x on top of the stack and push MEMORY[x]
					int address;
					try{
						address = memory[registers.get(arg2)+offset];		
					}catch (IndexOutOfBoundsException e){
						throw new MemoryAccessException("Cannot address this area. ");
					};
                	registers.put(arg1, address); //lw $r1 offset($r2)
                	break;
                case "sw":															//	sw $r1 offset($r2)  ----> L'azione di store word prende il contenuto di un registro e lo memorizza all'interno della memoria.
                	memory[registers.get(arg2)+offset] = registers.get(arg1); 		//non sono sicura della posizione di memoria a cui accediamo con memory[registers.get(arg2)+offset] //forse ok se r2 è sp o hp
					//registers.put("$a0", registers.get(arg1) );
                	break;
                case "li":
                	registers.put(arg1,Integer.parseInt(arg2));
                	break;
                case "mv":
                	registers.put(arg1, registers.get(arg2));
                	break;
                case "add":
                	registers.put(arg1, registers.get(arg2) + registers.get(arg3));
                	break;
                case "sub":
                	registers.put(arg1, registers.get(arg2) - registers.get(arg3));
                	break;
                case "mul":
                	registers.put(arg1, registers.get(arg2) * registers.get(arg3));
                	break;
                case "div":
                	registers.put(arg1, registers.get(arg2) / registers.get(arg3));
                	break;
                case "addi":
                	registers.put(arg1, registers.get(arg2) + Integer.parseInt(arg3));
                	break;
                case "subi":
                	registers.put(arg1, registers.get(arg2) - Integer.parseInt(arg3));
                	break;
                case "muli":
                	registers.put(arg1, registers.get(arg2) * Integer.parseInt(arg3));
                	break;
                case "divi":
                	registers.put(arg1, registers.get(arg2) / Integer.parseInt(arg3));
                	break;
                case "and":
                	if(registers.get(arg2) == 1 && registers.get(arg3) == 1)
                		registers.put(arg1, 1);
                	else
                		registers.put(arg1, null);
                	break;
                case "or":
                	if(registers.get(arg2) == 1 || registers.get(arg3) == 1)
                		registers.put(arg1, 1);
                	else
                		registers.put(arg1, null);
                	break;	
                case "not":
                	registers.put(arg1, registers.get(arg2) == 1 ? 0 : 1);
                	break;
                case "del":
                	memory[registers.get(arg1)] = -1;//wait how do we mark a cell as free?
                	break;
                case "print":
                	System.out.println(registers.get(arg1));
                	break;
                case "beq":
                	if(registers.get(arg1) == registers.get(arg2)) 
                		ip = Integer.parseInt(arg3);
                	break;
                case "bleq":
                	if(registers.get(arg1) <= registers.get(arg2))
                		ip = Integer.parseInt(arg3);
                	break;
                case "b":
					ip = Integer.parseInt(arg1);
                	break;
                case "jal":
                	registers.put("$ra", ip); //save the next instruction in $ra
					//label sono "label + num univoco", devo ragionare solo sul numero?
					ip = Integer.parseInt(arg1);
                	break;
                case "jr":
                	ip = registers.get(arg1);
                	break;
                case "halt":
                	System.out.println("MEMORIA");
                	for(int i : memory)
                		System.out.println(i);
                	return;
                default:
                	System.err.println("Wait, this assembly instruction is not recognized");
                	return;
                }
        	}
    	}
    }
}
