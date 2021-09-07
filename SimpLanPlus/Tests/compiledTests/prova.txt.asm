; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN ^int a = new int
	 li $t1 -1
 	 sw $t1 0($hp) 
	 push $a0
; END ^int a = new int
halt
; END BLOCK
