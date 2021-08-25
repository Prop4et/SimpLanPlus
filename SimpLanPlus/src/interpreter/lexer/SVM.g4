grammar SVM;

@lexer::header {
import java.util.List;
import java.util.ArrayList;
}
@lexer::members {
private List<String> errors = new ArrayList<>();
public int errorCount() {
	return errors.size();
}
}

assembly: (instruction)*;

instruction:
	'push' REGISTER													# push
	| 'pop'															# pop
	| 'lw' output = REGISTER NUMBER '(' input = REGISTER ')'		# loadWord
	| 'sw' output = REGISTER NUMBER '(' input = REGISTER ')'		# storeWord
	| 'li' REGISTER NUMBER											# loadInteger
	| 'add' output = REGISTER input1 = REGISTER input2 = REGISTER	# add
	| 'sub' output = REGISTER input1 = REGISTER input2 = REGISTER	# sub
	| 'mult' output = REGISTER input1 = REGISTER input2 = REGISTER	# mult
	| 'div' output = REGISTER input1 = REGISTER input2 = REGISTER	# div
	| 'addi' output = REGISTER input = REGISTER NUMBER				# addInt
	| 'subi' output = REGISTER input = REGISTER NUMBER				# subInt
	| 'multi' output = REGISTER input = REGISTER NUMBER				# multInt
	| 'divi' output = REGISTER input = REGISTER NUMBER				# divInt
	| 'and' output = REGISTER input1 = REGISTER input2 = REGISTER	# and
	| 'or' output = REGISTER input1 = REGISTER input2 = REGISTER	# or
	| 'not' output = REGISTER input = REGISTER						# not
	| 'andb' output = REGISTER input = REGISTER BOOL				# andBool
	| 'orb' output = REGISTER input = REGISTER BOOL					# orBool
	| 'notb' output = REGISTER BOOL									# notBool
	| 'mv' output = REGISTER input = REGISTER						# move
	| 'beq' input1 = REGISTER input2 = REGISTER LABEL				# branchIfEqual
	| 'bleq' input1 = REGISTER input2 = REGISTER LABEL				# branchIfLessEqual
	| 'b' LABEL														# branch
	| LABEL COL														# label
	| 'jal' LABEL													# jumpToFunction
	| 'jr' REGISTER													# jumpToRegister
	| 'del' REGISTER									            # delete
	| 'print' REGISTER												# print
	| 'halt'														# halt;




// LABELS
COL: ':';
LABEL: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
	)*;

// NUMBERS AND BOOLEANS
NUMBER: '0' | ('-')? (('1' ..'9') ('0' ..'9')*);
BOOL: '0' | '1';

//REGISTERS
REGISTER:
	'$a0'  //results in the accumulator
	| '$t1' //tmp register
	| '$sp' //top of the stack
	| '$fp' //points to al relative to the active frame
	| '$al' //static chain for scopes
	| '$ra' //return address where the address of the next instruction is saved
	| '$hp' //top of the heap
	;


// ESCAPE SEQUENCES
WS: ( '\t' | ' ' | '\r' | '\n')+ -> channel(HIDDEN);
LINECOMMENTS 	: ';' (~('\n'|'\r'))* -> skip;

ERR: . { errors.add("Invalid character: "+ getText()); } -> channel(HIDDEN);