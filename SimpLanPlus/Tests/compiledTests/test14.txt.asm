; NEW BLOCK 
	 push $sp
	 mv $al $fp
 	 push $al
; BEGIN int u = 1
	 li $a0 1
	 push $a0
; END int u = 1

push $fp
; BEGIN u EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END u EVAL 
push $a0
	 li $a0 6
push $a0
jal f	 halt
