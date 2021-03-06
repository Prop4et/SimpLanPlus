package interpreter;

import exceptions.MemoryAccessException;
import exceptions.NotInitializedVariableException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class MemoryCell {
	private Integer data;
	private boolean isUsed;

	public MemoryCell(int data, boolean isUsed) {
		this.data = data;
		this.isUsed = isUsed;
	}

	public MemoryCell() {
		data = null;
		isUsed = false;
	}

	public int getData() throws NotInitializedVariableException {
		if (data == null) {
			throw new NotInitializedVariableException("Trying to access not initialized memory cell.");
		} else {
			return data;
		}
	}

	public void setData(Integer data) {
		this.data = data;
		this.isUsed = true;
	}

	public void freeCell() {
		isUsed = false;
	}

	public boolean isFree() {
		return !isUsed;
	}

	@Override
	public String toString() {
		return "| " + (data != null && data >= 0 ? " " : "") + data + "\t|";
	}
}

public class ExecuteSVM {
	private final int memSize; // Max size of the memory (heap + stack)

    private final List<Instruction> code;
    private final MemoryCell[] memory;

    
    private int ip = 0;
    private HashMap<String, Integer> registers;
 
    
    public ExecuteSVM(int memSize, List<Instruction> code) {
    	this.memSize = memSize;			//memSize = heap + stack
										//heap starts from the top and grows downwards, while the stack starts from the bottom an grows upwards
										//neither of them as fixed side, we just need to check the they do not grows towards each other
        this.code = code;
        
        memory = new MemoryCell[memSize];
		for (int i = 0; i < memSize; i++) {
			memory[i] = new MemoryCell();
		}


		registers = new HashMap<>();
        registers.put("$sp", memSize);
        registers.put("$cl", memSize);
        registers.put("$fp", memSize -1);
        registers.put("$hp", 0);
        registers.put("$ra", null);
        registers.put("$al", null);
        registers.put("$a0", null);
        registers.put("$t1", null);
        
        }
	private int getFirstHeapMemoryCell() {
		int i = 0;
		while(!memory[i].isFree())
			i++;
		return i;
	}
	
	
    public void run() throws MemoryAccessException {
    	while(true) {
    		if(registers.get("$hp")+1>=registers.get("$sp")) {
    			throw  new MemoryAccessException("Error: Out of memory");
        		
        	}else {
        		Instruction bytecode = code.get(ip); // fetch
				ip++;
        		String arg1 = bytecode.getArg1();
                String arg2 = bytecode.getArg2();
                String arg3 = bytecode.getArg3();
                int offset = bytecode.getOffset();
                switch(bytecode.getInstruction()) {
                case "push":
					registers.put("$sp", registers.get("$sp") - 1);
                	memory[registers.get("$sp")].setData(registers.get(arg1));
                	
					//System.out.print("Printing \n");
					/*for(int i =0 ; i< memSize; i++ )
						System.out.println(i+ ": "+ memory[i].toString());*/
					break;
                case "pop":
                	//memory[registers.get("$sp")].setData(null);
					registers.put("$sp", registers.get("$sp") + 1);

                	break;
                case "lw":
                	//pop the value x on top of the stack and push MEMORY[x]
					int address;
					try{
						address = memory[registers.get(arg2)+offset].getData();
							 
					}catch (IndexOutOfBoundsException | NotInitializedVariableException e){
						System.out.println(registers.get(arg2)+offset);
						System.out.println("MEMORIA");
						for(int i =0 ; i< memSize; i++ )
							System.out.println(i+ ": "+ memory[i].toString());       
						throw new MemoryAccessException("Segmentation fault");
					}
                	registers.put(arg1, address); //lw $r1 offset($r2)
                	break;
                case "sw":

					if(arg2.equals("$hp")){
						int heapMemCell = getFirstHeapMemoryCell();
						memory[heapMemCell].setData(registers.get(arg1));
						registers.put("$a0", heapMemCell);			//					//after sw automatically save the value saved in $a0, it's essentially needed for pointer initialization
					}
					else
                		memory[registers.get(arg2)+offset].setData(registers.get(arg1));

					/*if(arg1.equals("$ra") && arg2.equals("$cl")){
						System.out.println("SW RA: " + registers.get(arg1) + " cl " + registers.get(arg2) +  " offset: " + offset);
					}*/
					break;
                case "li":
                	registers.put(arg1,Integer.parseInt(arg2));
                	break;
                case "mv":
                	registers.put(arg1, registers.get(arg2));
					//System.out.print("get al: " +registers.get(arg2));
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
                	memory[registers.get(arg1)].freeCell();//wait how do we mark a cell as free?
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
                	//System.out.println("previous ip " + ip +" next ip " +arg1);
                	ip = Integer.parseInt(arg1);
                	break;
                case "jr":
                	//System.out.println("jr: " + registers.get(arg1));
                	ip = registers.get(arg1);
                	break;
                case "halt":
                	/*System.out.println("MEMORIA");
					for(int i =0 ; i< memSize; i++ )
						System.out.println(i+ ": "+ memory[i].toString());*/
                	 
					return;
                default:
                	System.err.println("Unrecognized instruction: " + bytecode.getInstruction());
                	return;
                }
        	}
    	}
    }
}
