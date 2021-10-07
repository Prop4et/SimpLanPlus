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
	 li $a0 5
push $a0 ; pushing 5
	 li $a0 4
push $a0 ; pushing 4
mv $fp $sp
addi $fp $fp 2
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
b endendf
f:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN IF ; BEGIN m > n
; BEGIN m EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END m EVAL 
	 push $a0 ; push on the stack e1
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END n EVAL 
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 bleq $t1 $a0 greaterTrueBranch10
	 li $a0 1
	 b endgreaterTrueBranch10
	 greaterTrueBranch10:
	 li $a0 0
	 endgreaterTrueBranch10:
	 ; END m > n
 	 li $t1 1
	 beq $a0 $t1 then9
; BEGIN ELSE BRANCH 
; NEW BLOCK 
push $fp ;push old fp
push $cl
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
; BEGIN int x = 1
	 li $a0 1
	 push $a0
; END int x = 1
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 1 ;frame pointer before decs (n =: 1)
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
lw $al 0($fp)
push $al
; BEGIN m + 1
; BEGIN m EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -1($al)
; END m EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END m + 1
push $a0 ; pushing m + 1
; BEGIN n + 1
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -2($al)
; END n EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END n + 1
push $a0 ; pushing n + 1
mv $fp $sp
addi $fp $fp 2
jal f; END CALLING f
addi $sp $sp 1 ;pop var declarations
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
; BEGIN m + n
; BEGIN m EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -1($al)
; END m EVAL 
	 push $a0 ; push on the stack e1
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -2($al)
; END n EVAL 
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END m + n
print $a0
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
endf:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF f
endendf:
; END BLOCK
