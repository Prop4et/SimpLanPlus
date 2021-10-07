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
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
	 li $a0 2
push $a0 ; pushing 2
mv $fp $sp
addi $fp $fp 1
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
b endendf
f:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
print $a0
; RETURN 
	 b endf
;END RETURN 
; END BLOCK
endf:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF f
endendf:
; END BLOCK
