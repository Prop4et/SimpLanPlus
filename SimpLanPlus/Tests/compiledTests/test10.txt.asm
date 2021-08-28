; NEW BLOCK 
	 push $sp
	 mv $al $fp
 	 push $al
f:
mv $fp $sp
push $ra
; NEW BLOCK 
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 0($al)
; END x EVAL 
	 del $a0
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -4($al)
; END y EVAL 
	 del $a0
lw $ra 0($sp)
addi $sp $sp 2
lw $fp 0($sp)
 pop
 jr $ra
; BEGIN ^int x = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int x = new int
push $fp
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -4($al)
; END x EVAL 
push $a0
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -4($al)
; END x EVAL 
push $a0
lw $al 0($fp)
push $al
jal f	 halt
