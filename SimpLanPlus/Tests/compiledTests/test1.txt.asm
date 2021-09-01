; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN int c = 1
	 li $a0 1
	 push $a0
; END int c = 1
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END c EVAL 
push $a0 ; pushing c
mv $fp $sp
addi $fp $fp1
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
f:
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
;END DEFINITION OF f
