; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN int b = 78
	 li $a0 78
	 push $a0
; END int b = 78
; BEGIN int a = 99
	 li $a0 99
	 push $a0
; END int a = 99
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $sp $sp -1
lw $al 0($fp)
push $al
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END a EVAL 
push $a0 ; pushing a
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END b EVAL 
push $a0 ; pushing b
mv $fp $sp
addi $fp $fp 2
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
f:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END x EVAL 
print $a0
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END y EVAL 
print $a0
endf:
lw $ra -1($cl)
lw $fp 1($cl)
addi $sp $sp 2
jr $ra
;END DEFINITION OF f
