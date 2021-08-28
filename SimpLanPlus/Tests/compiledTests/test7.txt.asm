; NEW BLOCK 
	 push $sp
	 mv $al $fp
 	 push $al
; BEGIN int a
	 addi $sp $sp -1
; END int a
; NEW BLOCK 
	 push $fp
	 mv $al $fp
 	 push $al
; BEGIN int b
	 addi $sp $sp -1
; END int b
; BEGIN a = a + b
; BEGIN a + b
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 0($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END b EVAL 
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END a + b
	 push $a0
; BEGIN DEREFERENCE NODE 
	 mv $al $fp
	 lw $al 0($al)
 	 addi $a0 $al 0
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a = a + b
; RETURN 
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 0($al)
; END a EVAL 
	 b null
;END RETURN 
	 halt
