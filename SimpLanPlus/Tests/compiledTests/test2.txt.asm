; NEW BLOCK 
	 push $sp
	 mv $al $fp
 	 push $al
; BEGIN int x = 2
	 li $a0 2
	 push $a0
; END int x = 2
; NEW BLOCK 
	 push $fp
	 mv $al $fp
 	 push $al
; BEGIN int y
	 addi $sp $sp -1
; END int y
	 halt
