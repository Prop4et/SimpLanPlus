; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN ^int x = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int x = new int
; BEGIN ^int y = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int y = new int
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
lw $al 0($fp)
push $al
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END x EVAL 
push $a0 ; pushing x
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END y EVAL 
push $a0 ; pushing y
mv $fp $sp
addi $fp $fp 2
jal f; END CALLING f
halt
; BEGIN DEFINITION OF f:
f:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END x EVAL 
	 del $a0
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END y EVAL 
	 del $a0
; BEGIN x EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END x EVAL 
	 del $a0
; BEGIN y EVAL 
 	 mv $al $fp 
	 lw $a0 -2($al)
; END y EVAL 
	 del $a0
; END BLOCK
endf:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF f
; END BLOCK
