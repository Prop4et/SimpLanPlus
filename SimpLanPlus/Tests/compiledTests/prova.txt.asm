; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN ^int u = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int u = new int
; BEGIN u^ = 1
	 li $a0 1
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -1
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END u^ = 1
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN u EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END u EVAL 
push $a0 ; pushing u
	 li $a0 1
push $a0 ; pushing 1
mv $fp $sp
addi $fp $fp 2
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
f:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN IF ; BEGIN n > 0
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END n EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 0
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 bleq $t1 $a0 greaterTrueBranch2
	 li $a0 1
	 b endgreaterTrueBranch2
	 greaterTrueBranch2:
	 li $a0 0
	 endgreaterTrueBranch2:
	 ; END n > 0
 	 li $t1 1
	 beq $a0 $t1 then1
	 b endifthen1
	then1:
; THAN BRANCH 
; NEW BLOCK 
push $fp ;push old fp
push $cl
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -1($al)
; END x EVAL 
push $a0 ; pushing x
; BEGIN n - 1
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -2($al)
; END n EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 sub $a0 $t1 $a0
	 ; END n - 1
push $a0 ; pushing n - 1
mv $fp $sp
addi $fp $fp 2
jal f; END CALLING f
; RETURN 
	 b endf
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
endifthen1 :
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
; END BLOCK
