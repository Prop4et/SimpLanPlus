; NEW BLOCK 
push $sp
li $t1 0; making space for ra
push $t1; pushed ra
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN CALLING f
push $fp
subi $sp $sp 1; RA
lw $al 0($fp)
push $al
	 li $a0 1
push $a0
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 1 ;frame pointer before decs (n =: 1)
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
f:
sw $ra 0($fp); save ra
; NEW BLOCK 
lw $ra 0($sp)
addi $sp $sp 1
lw $fp 0($sp)
 pop
 jr $ra
;END DEFINITION OF f
