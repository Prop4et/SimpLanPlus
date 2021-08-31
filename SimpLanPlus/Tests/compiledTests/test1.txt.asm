; NEW BLOCK 
	 push $fp
 ; BEGIN int a = 8
	 li $a0 8
	 push $a0
; END int a = 8
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
	 lw $a0 -1($al)
; END a EVAL 
print $a0
pop
 ; BEGIN a = 78
	 li $a0 78
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -1
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
