; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN ^^int x
	 addi $sp $sp -1
; END ^^int x
; BEGIN ^int y = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int y = new int
; BEGIN y^ = 1
	 li $a0 1
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -2
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END y^ = 1
; BEGIN x = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -1
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END x = new int
; BEGIN x^ = y
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END y EVAL 
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -1
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END x^ = y
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END x EVAL 
 	 lw $a0 0($a0)
 	 lw $a0 0($a0)
print $a0
halt
; END BLOCK
