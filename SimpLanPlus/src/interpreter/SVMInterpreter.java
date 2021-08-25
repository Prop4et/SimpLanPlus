package interpreter;

import java.util.HashMap;
import java.util.List;

public class SVMInterpreter {
	private final int memSize; // Max size of the memory (heap + stack)

    private final List<Instruction> code;
    private final int[] memory;

    
    private int ip = 0;
    private HashMap<String, Integer> registers;
    private int sp;
    private int hp = 0;
    private int fp;
    private int ra;
    private int al;
    private int a0;
    private int t1;
    
    public SVMInterpreter(int memSize, List<Instruction> code) {
    	this.memSize = memSize;
        this.code = code;
        
        memory = new int[memSize];

        registers = new HashMap<>();
        registers.put("sp", memSize);
        registers.put("fp", memSize);
        registers.put("hp", 0);
        registers.put("ra", null);
        registers.put("al", null);
        registers.put("a0", null);
        registers.put("t1", null);
        
        }
    
    public void run() {
    	while(true) {
    		if(hp+1>=sp) {
        		System.out.println("\nError: Out of memory");
                return;
        	}else {
        		Instruction bytecode = code.get(ip); // fetch
        		ip++;
        		String arg1 = bytecode.getArg1();
                String arg2 = bytecode.getArg2();
                String arg3 = bytecode.getArg3();
                int offset = bytecode.getOffset();
                switch(bytecode.getInstruction()) {
                case "push":
                	sp -= 4; //why don't we just use 1 since everything has the same dimension?
                	memory[sp] = registers.get(arg1); //push $r1
                	break;
                case "pop":
                	sp += 4;
                	break;
                case "lw":
                	registers.put(arg1, memory[registers.get(arg2)+offset]); //lw $r1 offset($r2)
                	break;
                case "sw":
                	memory[registers.get(arg2)+offset] = registers.get(arg1); //sw $r1 offset($r2)
                	break;
                case "li":
                	memory[registers.get(arg1)] = Integer.parseInt(arg2);
                	break;
                case "mv":
                	memory[registers.get(arg1)] = memory[registers.get(arg2)];
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
                	//arg1 and arg2 could only be 0 or 1, if they're the same then the or is true
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
                	if(registers.get(arg1) == registers.get(arg2)) {
                		//wait how do we jump?
                	}
                	break;
                case "bleq":
                	if(registers.get(arg1) <= registers.get(arg2)) {
                		//wait how do we jump?
                	}
                	break;
                case "b":
                	break;
                case "jal":
                	registers.put("ra", ip); //save the next instruction in ra
                	//HOW DO WE JUMP
                	break;
                case "jr":
                	ip = registers.get(arg1);
                	break;
                case "halt":
                	return;
                default:
                	System.err.println("Wait, this assembly instruction is not recognized");
                	return;
                }
        	}
    	}
    }
}
