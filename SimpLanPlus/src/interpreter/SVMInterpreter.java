package interpreter;

import java.util.List;

public class SVMInterpreter {
	private final int memSize; // Max size of the memory (heap + stack)

    private final List<Instruction> code;
    private final int[] memory;

    
    private int ip = 0;
    
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
        
        sp = memSize;
        fp = memSize;
        
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
                
                //missing case, grammar first
                
                }
        	}
    	}
    }
}
