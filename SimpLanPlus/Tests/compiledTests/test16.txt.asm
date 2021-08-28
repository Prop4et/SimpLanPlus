; NEW BLOCK 
	 push $sp
	 mv $al $fp
 	 push $al
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
 	 addi $a0 $al 0
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END u^ = 1
; BEGIN IF ; BEGIN u^ == 0
; BEGIN u EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END u EVAL 
 	 lw $a0 0($a0)
	 push $a0 ; push on the stack e1
	 li $a0 0
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 beq $t1 $a0 equalTrueBranch2
	 li $a0 0 ;e1 != e2
	 b endequalTrueBranch2
	 equalTrueBranch2:
	 li $a0 1 ;e1 == e2
	 endequalTrueBranch2:
	 ; END u^ == 0
 	 li $t1 1
	 beq $a0 $t1 then1
	 b endifthen1
	then1:
; THAN BRANCH 
; BEGIN u EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END u EVAL 
 	 lw $a0 0($a0)
print $a0
 ;END THAN BRANCH 
; END IF 
endifthen1 :
; BEGIN CALLING f
push $fp
	 li $a0 6
push $a0
; BEGIN u EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END u EVAL 
push $a0
lw $al 0($fp)
push $al
jal f; END CALLING f
;; BEGIN ^int u = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int u = new int
	 halt
