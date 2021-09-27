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
	 li $a0 4
push $a0 ; pushing 4
	 li $a0 7
push $a0 ; pushing 7
mv $fp $sp
addi $fp $fp 2
jal max; END CALLING max
print $a0
halt
; BEGIN DEFINITION OF max:
max:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN IF ; BEGIN a > b
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
	 bleq $t1 $a0 greaterTrueBranch10
	 li $a0 1
	 b endgreaterTrueBranch10
	 greaterTrueBranch10:
	 li $a0 0
	 endgreaterTrueBranch10:
	 ; END a > b
 	 li $t1 1
	 beq $a0 $t1 then9
; BEGIN ELSE BRANCH 
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
 ;END ELSE BRANCH 
	 b endifthen9
	then9:
; THAN BRANCH 
; NEW BLOCK 
push $fp ;push old fp
push $cl
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; RETURN 
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -1($al)
; END a EVAL 
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
 ;END THAN BRANCH 
; END IF 
endifthen9 :
; END BLOCK
endmax:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF max
; END BLOCK
