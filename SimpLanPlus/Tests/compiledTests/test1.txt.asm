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
push $sp
mv $cl $sp
addi $sp $sp -1
lw $al 0($fp)
push $al
jal f; END CALLING f
;halt
; BEGIN DEFINITION OF f:
f:
sw $ra -1($cl)
; NEW BLOCK 
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl)
addi $sp $sp 0
addi $cl $fp 2
jr $ra
;END DEFINITION OF f
