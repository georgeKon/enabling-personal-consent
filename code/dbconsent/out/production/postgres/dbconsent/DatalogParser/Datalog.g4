grammar Datalog;

query
	: head COLONDASH predicate (COMMA predicate)*
	;

head
	:	PREDICATE_NAME LPAREN (VARIABLE (COMMA VARIABLE)*)? RPAREN
	;

predicate
	:	PREDICATE_NAME LPAREN predicateArgument (COMMA predicateArgument)* RPAREN
	;

predicateArgument
	: VARIABLE
	| INTEGER_CONST
	| NULL_CONST
	| BOOLEAN_CONST
	| FLOAT_CONST
	| STRING_CONST
	;

WHITESPACE          : [ \t\r\n]+    -> skip;
BLOCK_COMMENT       : '/*' .*? '*/' -> skip;
LINE_COMMENT        : '--' .*? '\n' -> skip;

LPAREN: '(';
RPAREN: ')';
COMMA : ',';
PERIOD: '.';
COLONDASH: ':-';
fragment UNDERSCORE: '_';

PREDICATE_NAME : UPPERCASE+;

VARIABLE : (UPPERCASE | LOWERCASE) (UPPERCASE | LOWERCASE | DIGIT | UNDERSCORE)* ;

STRING_CONST
    : '"' ( '\\'. | '""' | ~('"' | '\\') )* '"'
    | '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\''
    ;

BOOLEAN_CONST : TRUE_CONST | FALSE_CONST;

INTEGER_CONST : DIGIT+;

FLOAT_CONST : DIGIT+ PERIOD DIGIT+;

fragment TRUE_CONST : 'True';
fragment FALSE_CONST : 'False';
NULL_CONST : 'Null';
fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];
fragment DIGIT : [0-9];
fragment SQUOTE : '\'';
fragment DQUOTE : '"';
fragment SYMBOL : [-!$£%^&*()_+\\|~=`{}[\]:";'<>?,./#@];
