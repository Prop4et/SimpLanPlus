; NEW BLOCK 
; BEGIN int a = 1
	 li $a0 1
	 push $a0
; END int a = 1
; NEW BLOCK 
	 push $fp
 mv $fp $sp
; BEGIN int b = 2
	 li $a0 2
	 push $a0
; END int b = 2
; NEW BLOCK 
	 push $fp
 mv $fp $sp
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $al 0($al)
	 lw $a0 0($al)
; END a EVAL 
print $a0
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 0($al)
; END b EVAL 
print $a0
pop
 pop
 	 halt
