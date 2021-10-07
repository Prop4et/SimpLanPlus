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
; BEGIN a^ = 10
	 li $a0 10
	 push $a0
; BEGIN  DEREFERANTION NODE 
	 mv $al $fp
 	 addi $a0 $al -1
 	 lw $a0 0($a0)
	 lw $t1 0($sp)
	 pop
	 sw $t1 0($a0)
; END a^ = 10
; BEGIN CALLING f
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
 	 lw $a0 0($a0)
push $a0 ; pushing a^
mv $fp $sp
addi $fp $fp 1
jal f; END CALLING f
print $a0
halt
; BEGIN DEFINITION OF f:
b endendf
f:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN IF ; BEGIN CALLING g
push $fp
push $sp
mv $cl $sp
addi $t1 $cl 2
sw $t1 0($cl)
addi $sp $sp -1
mv $al $fp
push $al
; BEGIN a EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END a EVAL 
push $a0 ; pushing a
mv $fp $sp
addi $fp $fp 1
jal g; END CALLING g
 	 li $t1 1
	 beq $a0 $t1 then3
; BEGIN ELSE BRANCH 
; NEW BLOCK 
push $fp ;push old fp
push $cl
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; RETURN 
	 li $a0 1
	 b endf
;END RETURN 
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
pop ;pop consistency ra
lw $cl 0($sp)
pop
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
; END BLOCK
 ;END ELSE BRANCH 
	 b endifthen3
	then3:
; THAN BRANCH 
; RETURN 
	 li $a0 2
	 b endf
;END RETURN 
 ;END THAN BRANCH 
; END IF 
endifthen3 :
; BEGIN DEFINITION OF g:
b endendg
g:
sw $ra -1($cl)
; NEW BLOCK 
; BEGIN IF ; BEGIN b >= 10
; BEGIN b EVAL 
 	 mv $al $fp 
	 lw $a0 -1($al)
; END b EVAL 
	 push $a0 ; push on the stack e1
	 li $a0 10
	 lw $t1 0($sp) ;$t1 = e1, $a0 = e2
	 pop ;pop e1 from the stack
	 beq $t1 $a0 equalTrueBranch5
	 bleq $t1 $a0 lesseqTrueBranch6
	 li $a0 1
	 b endlesseqTrueBranch6
	 lesseqTrueBranch6:
	 li $a0 0
	 endlesseqTrueBranch6:
	 b endequalTrueBranch5
	 equalTrueBranch5:
	 li $a0 1
	 endequalTrueBranch5:
	 ; END b >= 10
 	 li $t1 1
	 beq $a0 $t1 then4
; BEGIN ELSE BRANCH 
; NEW BLOCK 
push $fp ;push old fp
push $cl
subi $sp $sp 1; ra 
mv $al $fp
push $al ;it's equal to the old $fp
mv $fp $sp; frame pointer above the new declarations
addi $fp $fp 0 ;frame pointer before decs (n =: 0)
; RETURN 
	 li $a0 0
	 b endg
;END RETURN 
addi $sp $sp 0 ;pop var declarations
pop ;pop $al
pop ;pop consistency ra
lw $cl 0($sp)
pop
lw $fp 0($sp) ;restore old $fp
pop ;pop old $fp
; END BLOCK
 ;END ELSE BRANCH 
	 b endifthen4
	then4:
; THAN BRANCH 
; RETURN 
	 li $a0 1
	 b endg
;END RETURN 
 ;END THAN BRANCH 
; END IF 
endifthen4 :
; END BLOCK
endg:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF g
endendg:
; END BLOCK
endf:
lw $ra -1($cl)
lw $fp 1($cl)
lw $sp 0($cl) 
addi $cl $fp 2
jr $ra
;END DEFINITION OF f
endendf:
; END BLOCK
