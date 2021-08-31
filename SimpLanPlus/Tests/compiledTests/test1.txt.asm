; NEW BLOCK 
push $sp
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN int a = 99
	 li $a0 99
	 push $a0
; END int a = 99
; BEGIN int b = 3
	 li $a0 3
	 push $a0
; END int b = 3
; NEW BLOCK 
push $fp ;push old fp
mv $al $fp
push $al ;it's equal to the old $fp
; BEGIN int c
	 addi $sp $sp -1
; END int c
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 1 ;frame pointer before decs (n =: 1)
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -1($al)
; END a EVAL 
print $a0
; NEW BLOCK 
push $fp ;push old fp
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; BEGIN c = 8
	 li $a0 8
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
	 lw $al 0($al)
 	 addi $a0 $al 0
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END c = 8
; BEGIN a = 98
	 li $a0 98
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
	 lw $al 0($al)
	 lw $al 0($al)
 	 addi $a0 $al 0
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a = 98
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
	 lw $a0 -2($al)
; END b EVAL 
print $a0
; NEW BLOCK 
push $fp ;push old fp
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; BEGIN a = 97
	 li $a0 97
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
 	 addi $a0 $al 0
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a = 97
; BEGIN IF ; BEGIN a != b
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -2($al)
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
push $fp ;push old fp
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; BEGIN a = 95
	 li $a0 95
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
 	 addi $a0 $al 0
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a = 95
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END a EVAL 
print $a0
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -2($al)
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
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
 ;END ELSE BRANCH 
	 b endifthen1
	then1:
; THAN BRANCH 
; NEW BLOCK 
push $fp ;push old fp
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; BEGIN a = 96
	 li $a0 96
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
 	 addi $a0 $al 0
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a = 96
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 -1($al)
; END c EVAL 
print $a0
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
 ;END THAN BRANCH 
; END IF 
endifthen1 :
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
addi $sp $sp 1 ;pop var declarations
pop ;pop $al
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
; BEGIN a = 78
	 li $a0 78
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al 0
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a = 78
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
print $a0
halt
