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
	 li $a0 8
push $a0 ; pushing 8
mv $fp $sp
addi $fp $fp 1
jal f; END CALLING f
print $a0
halt
; BEGIN DEFINITION OF f:
f:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN IF ; BEGIN a <= 1
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 bleq $t1 $a0lesseqTrueBranch2
	 li $a0 0
	 b endlesseqTrueBranch2
	 lesseqTrueBranch2:
	 li $a0 1
	 endlesseqTrueBranch2:
	 ; END a <= 1
 	 li $t1 1
	 beq $a0 $t1 then1
; BEGIN ELSE BRANCH 
; RETURN 
; BEGIN call: f(a - 1, ) + call: f(a - 2, )
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN a - 1
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 sub $a0 $t1 $a0
	 ; END a - 1
push $a0 ; pushing a - 1
mv $fp $sp
addi $fp $fp 1
jal f; END CALLING f
	 push $a0 ; push on the stack e1
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN a - 2
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 2
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 sub $a0 $t1 $a0
	 ; END a - 2
push $a0 ; pushing a - 2
mv $fp $sp
addi $fp $fp 1
jal f; END CALLING f
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END call: f(a - 1, ) + call: f(a - 2, )
	 b endf
;END RETURN 
 ;END ELSE BRANCH 
	 b endifthen1
	then1:
; THAN BRANCH 
; RETURN 
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
	 b endf
;END RETURN 
 ;END THAN BRANCH 
; END IF 
endifthen1 :
; END BLOCK
endf:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF f
; END BLOCK
