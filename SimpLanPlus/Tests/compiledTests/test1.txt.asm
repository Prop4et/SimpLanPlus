; NEW BLOCK 
push $sp
li $t1 0; making space for ra
push $t1; pushed ra
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
<<<<<<< HEAD
; BEGIN CALLING f
push $fp
subi $sp $sp 1; RA
lw $al 0($fp)
push $al
	 li $a0 1
push $a0
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 1 ;frame pointer before decs (n =: 1)
=======
; BEGIN int c = 1
	 li $a0 1
	 push $a0
; END int c = 1
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $sp $sp -1
lw $al 0($fp)
push $al
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END c EVAL 
push $a0
mv $fp $sp
addi $fp $fp1
>>>>>>> cl
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
f:
<<<<<<< HEAD
sw $ra 0($fp); save ra
; NEW BLOCK 
lw $ra 0($sp)
addi $sp $sp 1
lw $fp 0($sp)
 pop
 jr $ra
=======
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
print $a0
endf:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl)
addi $sp $sp 1
jr $ra
>>>>>>> cl
;END DEFINITION OF f
