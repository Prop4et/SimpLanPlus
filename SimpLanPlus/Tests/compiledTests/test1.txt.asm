; NEW BLOCK 
push $sp
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN CALLING f
push $fp
lw $al 0($fp)
push $al
jal f; END CALLING f
;halt
; BEGIN DEFINITION OF f:
f:
mv $fp $sp
push $ra
; NEW BLOCK 
	 li $a0 1
print $a0
lw $ra 0($sp)
addi $sp $sp 0
lw $fp 0($sp)
 pop
 jr $ra
;END DEFINITION OF f
