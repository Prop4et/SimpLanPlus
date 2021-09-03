; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN int x = 10
	 li $a0 10
	 push $a0
; END int x = 10
; BEGIN int y
	 addi $sp $sp -1
; END int y
; BEGIN y = 23
	 li $a0 23
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -2
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END y = 23
; BEGIN CALLING diff
push $fp
push $sp
mv $cl $sp
addi $sp $sp -1
lw $al 0($fp)
push $al
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END y EVAL 
push $a0 ; pushing y
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END y EVAL 
push $a0 ; pushing y
mv $fp $sp
addi $fp $fp 2
jal diff; END CALLING diff
print $a0
halt
; BEGIN DEFINITION OF diff:
diff:
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
	 bleq $t1 $a0 greaterTrueBranch2
	 li $a0 1
	 b endgreaterTrueBranch2
	 greaterTrueBranch2:
	 li $a0 0
	 endgreaterTrueBranch2:
	 ; END a > b
 	 li $t1 1
	 beq $a0 $t1 then1
; BEGIN ELSE BRANCH 
; RETURN 
; BEGIN b - a
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END b EVAL 
	 push $a0 ; push on the stack e1
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 sub $a0 $t1 $a0
	 ; END b - a
	 b enddiff
;END RETURN 
 ;END ELSE BRANCH 
	 b endifthen1
	then1:
; THAN BRANCH 
; RETURN 
; BEGIN a - b
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
	 sub $a0 $t1 $a0
	 ; END a - b
	 b enddiff
;END RETURN 
 ;END THAN BRANCH 
; END IF 
endifthen1 :
enddiff:
lw $ra -1($cl)
lw $fp 1($cl)
addi $sp $sp 2
jr $ra
;END DEFINITION OF diff
