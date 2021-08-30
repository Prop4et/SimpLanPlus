; NEW BLOCK 
; BEGIN int a = 1
	 li $a0 1
	 push $a0
; END int a = 1
; BEGIN IF ; BEGIN a > 1
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 bleq $t1 $a0greaterTrueBranch2
	 li $a0 1
	 b endgreaterTrueBranch2
	 greaterTrueBranch2:
	 li $a0 0
	 endgreaterTrueBranch2:
	 ; END a > 1
 	 li $t1 1
	 beq $a0 $t1 then1
; BEGIN ELSE BRANCH 
	 li $a0 0
print $a0
 ;END ELSE BRANCH 
	 b endifthen1
	then1:
; THAN BRANCH 
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END a EVAL 
print $a0
 ;END THAN BRANCH 
; END IF 
endifthen1 :
	 halt
