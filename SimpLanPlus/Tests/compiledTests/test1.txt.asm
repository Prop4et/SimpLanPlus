; NEW BLOCK 
; BEGIN DEFINITION OF sum:
sum:
mv $fp $sp
push $ra
; NEW BLOCK 
; RETURN 
; BEGIN a + b
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END b EVAL 
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END a + b
	 b null
;END RETURN 
lw $ra 0($sp)
addi $sp $sp 2
lw $fp 0($sp)
 pop
 jr $ra
;END DEFINITION OF sum
; BEGIN DEFINITION OF sum:
sum:
mv $fp $sp
push $ra
; NEW BLOCK 
; RETURN 
; BEGIN a + b
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END b EVAL 
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END a + b
	 b null
;END RETURN 
lw $ra 0($sp)
addi $sp $sp 2
lw $fp 0($sp)
 pop
 jr $ra
;END DEFINITION OF sum
	 halt
