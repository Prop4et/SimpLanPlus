; NEW BLOCK 
	 push $sp
	 mv $al $fp
 	 push $al
; BEGIN int c = 7
	 li $a0 7
	 push $a0
; END int c = 7
; BEGIN int y = 7
	 li $a0 7
	 push $a0
; END int y = 7
; BEGIN IF ; BEGIN y == 4
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -4($al)
; END y EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 4
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 beq $t1 $a0 equalTrueBranch2
	 li $a0 0 ;e1 != e2
	 b endequalTrueBranch2
	 equalTrueBranch2:
	 li $a0 1 ;e1 == e2
	 endequalTrueBranch2:
	 ; END y == 4
 	 li $t1 1
	 beq $a0 $t1 then1
; BEGIN ELSE BRANCH 
; NEW BLOCK 
	 push $fp
	 mv $al $fp
 	 push $al
; RETURN 
	 li $a0 5
	 b null
;END RETURN 
 ;END ELSE BRANCH 
	 b endifthen1
	then1:
; THAN BRANCH 
; NEW BLOCK 
	 push $fp
	 mv $al $fp
 	 push $al
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -4($al)
; END y EVAL 
	 del $a0
; RETURN 
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 0($al)
; END c EVAL 
	 b null
;END RETURN 
 ;END THAN BRANCH 
; END IF 
endifthen1 :
; RETURN 
; BEGIN c EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END c EVAL 
	 b null
;END RETURN 
; BEGIN int c = 7
	 li $a0 7
	 push $a0
; END int c = 7
; BEGIN int y = 7
	 li $a0 7
	 push $a0
; END int y = 7
	 halt
