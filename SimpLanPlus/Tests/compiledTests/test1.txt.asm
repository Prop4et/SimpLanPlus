; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $sp $sp -1
lw $al 0($fp)
push $al
	 li $a0 10
push $a0 ; pushing 10
mv $fp $sp
addi $fp $fp 1
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
f:
sw $ra -1($cl)
; NEW BLOCK 
; RETURN 
; BEGIN a + 8
; BEGIN a EVAL 
 	 lw $al 0($fp) 
	 lw $a0 -1($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 8
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END a + 8
	 b null
;END RETURN 
endf:
lw $ra -1($cl)
lw $fp 1($cl)
addi $sp $sp 1
jr $ra
;END DEFINITION OF f
