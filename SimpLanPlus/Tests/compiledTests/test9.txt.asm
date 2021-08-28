; NEW BLOCK 
	 push $sp
	 mv $al $fp
 	 push $al
; BEGIN ^int y = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int y = new int
; BEGIN ^int x = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int x = new int
; BEGIN x^ = 1
	 li $a0 1
	 push $a0
; BEGIN DEREFERENCE NODE 
	 mv $al $fp
 	 addi $a0 $al -4
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END x^ = 1
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -4($al)
; END x EVAL 
	 del $a0
; BEGIN y^ = x^
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -4($al)
; END x EVAL 
 	 lw $a0 0($a0)
	 push $a0
; BEGIN DEREFERENCE NODE 
	 mv $al $fp
 	 addi $a0 $al 0
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END y^ = x^
	 halt
