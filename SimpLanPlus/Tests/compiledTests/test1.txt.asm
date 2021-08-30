; NEW BLOCK 
; BEGIN int a = 4
	 li $a0 4
	 push $a0
; END int a = 4
; NEW BLOCK 
	 push $fp
 mv $fp $sp
; BEGIN int b = 3
	 li $a0 3
	 push $a0
; END int b = 3
; NEW BLOCK 
	 push $fp
 mv $fp $sp
; BEGIN int c
	 addi $sp $sp -1
; END int c
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 0($al)
; END a EVAL 
print $a0
; NEW BLOCK 
	 push $fp
 mv $fp $sp
; BEGIN c = 8
	 li $a0 8
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
	 lw $al 0($al)
 	 addi $a0 $al -1
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END c = 8
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -1($al)
; END c EVAL 
print $a0
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END b EVAL 
print $a0
; NEW BLOCK 
	 push $fp
 mv $fp $sp
; BEGIN IF ; BEGIN a != b
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 0($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END b EVAL 
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 beq $t1 $a0 unequalTrueBranch2
	 li $a0 1 
	 b endunequalTrueBranch2
	 unequalTrueBranch2:
	 li $a0 0 
	 endunequalTrueBranch2:
	 ; END a != b
 	 li $t1 1
	 beq $a0 $t1 then1
; BEGIN ELSE BRANCH 
; NEW BLOCK 
	 push $fp
 mv $fp $sp
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 0($al)
; END a EVAL 
print $a0
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END b EVAL 
print $a0
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END c EVAL 
print $a0
pop
  ;END ELSE BRANCH 
	 b endifthen1
	then1:
; THAN BRANCH 
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END c EVAL 
print $a0
 ;END THAN BRANCH 
; END IF 
endifthen1 :
pop
 pop
 pop
 pop
 	 halt
