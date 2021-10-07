; NEW BLOCK 
push $sp
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; bring up the frame pointer
sw $fp 0($fp); save the old value
; BEGIN CALLING fib
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
	 li $a0 8
push $a0 ; pushing 8
mv $fp $sp
addi $fp $fp 1
jal fib; END CALLING fib
print $a0
halt
; BEGIN DEFINITION OF fib:
b endendfib
fib:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN IF ; BEGIN n <= 1
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END n EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 bleq $t1 $a0lesseqTrueBranch8
	 li $a0 0
	 b endlesseqTrueBranch8
	 lesseqTrueBranch8:
	 li $a0 1
	 endlesseqTrueBranch8:
	 ; END n <= 1
 	 li $t1 1
	 beq $a0 $t1 then7
	 b endifthen7
	then7:
; THAN BRANCH 
; NEW BLOCK 
push $fp ;push old fp
push $cl
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; RETURN 
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $al 0($al)
	 lw $a0 -1($al)
; END n EVAL 
	 b endfib
;END RETURN 
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
pop ;pop consistency ra
lw $cl 0($sp)
pop
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
; END BLOCK
 ;END THAN BRANCH 
; END IF 
endifthen7 :
; RETURN 
; BEGIN call: fib(n - 1, ) + call: fib(n - 2, )
; BEGIN CALLING fib
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN n - 1
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END n EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 1
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 sub $a0 $t1 $a0
	 ; END n - 1
push $a0 ; pushing n - 1
mv $fp $sp
addi $fp $fp 1
jal fib; END CALLING fib
	 push $a0 ; push on the stack e1
; BEGIN CALLING fib
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN n - 2
; BEGIN n EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END n EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 2
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 sub $a0 $t1 $a0
	 ; END n - 2
push $a0 ; pushing n - 2
mv $fp $sp
addi $fp $fp 1
jal fib; END CALLING fib
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 add $a0 $t1 $a0
	 ; END call: fib(n - 1, ) + call: fib(n - 2, )
	 b endfib
;END RETURN 
; END BLOCK
endfib:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF fib
endendfib:
; END BLOCK
