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
	| 'lw' out = REGISTER offset = NUMBER '(' in = REGISTER ')'		# loadWord
	| 'sw' out = REGISTER offset = NUMBER '(' in = REGISTER ')'		# storeWord
	| 'li' REGISTER NUMBER											# loadInteger
	| 'add' out = REGISTER in = REGISTER in2 = REGISTER	# add
	| 'sub' out = REGISTER in = REGISTER in2 = REGISTER	# sub
	| 'mult' out = REGISTER in = REGISTER in2 = REGISTER	# mult
	| 'div' out = REGISTER in = REGISTER in2 = REGISTER	# div
	| 'addi' out = REGISTER in = REGISTER NUMBER				# addInt
	| 'subi' out = REGISTER in = REGISTER NUMBER				# subInt
	| 'multi' out = REGISTER in = REGISTER NUMBER				# multInt
	| 'divi' out = REGISTER in = REGISTER NUMBER				# divInt
	| 'and' out = REGISTER in = REGISTER in2 = REGISTER	# and
	| 'or' out = REGISTER in = REGISTER in2 = REGISTER	# or
	| 'not' out = REGISTER in = REGISTER						# not
	| 'andb' out = REGISTER in = REGISTER BOOL				# andBool
	| 'orb' out = REGISTER in = REGISTER BOOL					# orBool
	| 'notb' out = REGISTER BOOL									# notBool
	| 'mv' out = REGISTER in = REGISTER						# move
	| 'beq' in = REGISTER in2 = REGISTER LABEL				# branchIfEqual
	| 'bleq' in = REGISTER in2 = REGISTER LABEL				# branchIfLessEqual
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