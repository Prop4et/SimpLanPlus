; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN ^^int a = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^^int a = new int
; BEGIN ^int b = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int b = new int
; BEGIN b^ = 19
	 li $a0 19
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -2
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END b^ = 19
; BEGIN a^ = b
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END b EVAL 
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -1
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a^ = b
; BEGIN a^^ = b^
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END b EVAL 
 	 lw $a0 0($a0)
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -1
 	 lw $a0 0($a0)
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a^^ = b^
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END b EVAL 
 	 lw $a0 0($a0)
print $a0
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
 	 lw $a0 0($a0)
 	 lw $a0 0($a0)
print $a0
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
 	 lw $a0 0($a0)
print $a0
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
print $a0
halt
; END BLOCK
