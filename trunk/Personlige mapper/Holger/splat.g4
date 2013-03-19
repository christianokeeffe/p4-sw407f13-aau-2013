grammar SPLAT; 

program : roots;

roots : /* epsilon */ 
| root roots ;

root : dcl ';' 
| function 
| COMMENT ;

dcl : type LETTER dclend ;

type : PRIMITIVETYPE arraytype ;

PRIMITIVETYPE : 'bool' 
| 'double' 
| 'int' 
| 'char' 
| 'container' 
| 'string' ;

arraytype : '[ ]' | /* epsilon */;

LETTER : [a-zA-Z]+ ;

idend : LETTER idend 
| DIGIT idend 
|/* epsilon */ ;

dclend : /* epsilon */ 
| assign ;

assign : '<--' expr ;

expr : term exprend ;

term : comp termend ;

comp : factor compend ;

factor : '(' expr ')' 
| '!' '(' expr ')' 
| callid 
| numeric 
| string 
| functioncall 
| cast 
| 'LOW' 
| 'HIGH' 
| 'true' 
| 'false' ;

callid : LETTER arraycall ;

arraycall : '[' DIGIT ']' 
|/* epsilon */ ;

digits : /* epsilon */ 
| DIGIT digits ;

DIGIT : [1-9]+ ;

numeric : plusminus digitsnotempty numericend ;

plusminus : /* epsilon */ 
| '-' ;

digitsnotempty : DIGIT digits ;

numericend : /* epsilon */ 
| '.' digitsnotempty ;

string : '"'stringmidt'"' ;

stringmidt : LETTER stringmidt 
| symbol stringmidt 
| DIGIT stringmidt 
|/* epsilon */ ;

symbol : '!' 
| '%' 
| '^' 
| '&' 
| '(' 
| ')' 
| '_' 
| '+' 
| '|' 
| '~' 
| '-' 
| '=' 
| '`' 
| '{' 
| '}' 
| '[' 
| ']' 
| ':' 
| ';' 
| '?' 
| ',' 
| '.' 
| '/' 
| ' ' ;

functioncall : 'call' LETTER '(' callexpr ')' ;

callexpr : subcallexpr 
|/* epsilon */ ;

subcallexpr : expr subcallexprend ;

subcallexprend : ',' subcallexpr 
|/* epsilon */ ;

cast : type '(' expr ')' ;

compend : comparisonoperator comp 
|/* epsilon */ ;

comparisonoperator : '>' 
| '<' 
| '<=' 
| '>=' 
| '!=' 
| '=' ;

termend : '*' term 
| '/' term 
| 'AND' term 
|/* epsilon */ ;

exprend : '+' expr 
| '-' expr 
| 'OR' expr 
|/* epsilon */ ;

function : functionstart functionmidt ;

functionstart : 'function' LETTER 'return' ;

functionmidt : type functionend expr ';' 'end' 
| 'nothing' functionend 'nothing' ';' 'end' ;

functionend : 
'using' '(' params ')' 
'begin' 
stmts 
'return' ;

params : subparams 
|/* epsilon */ ;

subparams : type LETTER subparamsend ;

subparamsend : ',' subparams 
|/* epsilon */ ;

stmts : /* epsilon */ 
| stmt stmts ;

stmt : callid assign ';' 
| nontermif 
| nontermwhile 
| from 
| dcl ';' 
| functioncall ';' 
| nontermswitch 
| COMMENT ;

nontermif : 'if''(' expr ')' 
'begin' 
stmts 
'end' 
endif ;

endif : 
'else' nontermelse 
|/* epsilon */ ;

nontermelse : nontermif 
| 'begin' 
stmts 
'end' ;

nontermwhile : 'while' '(' expr ')' 
'begin' 
stmts 
'end' ;

from : 'from' expr to expr 'step' assign 
'begin' 
stmts 
'end' ;

nontermswitch : 'switch' '(' expr ')' 
'begin' 
cases 
'end' ;

cases : 'case' expr':' 
stmts 
endcase ;

endcase : cases 
| 'break' ';' breakend 
| 'default'':' 
stmts 
'break' ';' ;

breakend : cases 
| 'default'':' 
stmts 
'break' ';' 
|/* epsilon */ ;

COMMENT : '/*' .*? '*/' -> skip ;

WS : [ \r\t\n]+ -> skip ;OTHER: ' ' -> skip;