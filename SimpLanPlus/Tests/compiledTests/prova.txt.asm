; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN CALLING max
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
lw $al 0($fp)
push $al
	 li $a0 5
push $a0 ; pushing 5
	 li $a0 4
push $a0 ; pushing 4
mv $fp $sp
addi $fp $fp 2
jal max; END CALLING max
print $a0
halt
; BEGIN DEFINITION OF max:
max:
sw $ra -1($cl)
; NEW BLOCK 
; NEW BLOCK 
push $fp ;push old fp
push $cl
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; RETURN 
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -2($al)
; END b EVAL 
	 b endmax
;END RETURN 
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
pop ;pop consistency ra
lw $cl 0($sp)
pop
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
; END BLOCK
; END BLOCK
endmax:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF max
; END BLOCK
