grammar SPLAD; 
options {language = Java; }


program : roots ;

roots : /* epsilon */ 
| root roots ;

root : dcl ';' 
| callid assign ';' 
| function 
| COMMENT ;

dcl : type id dclend ;

type : PRIMITIVETYPE arraytype ;

PRIMITIVETYPE : 'bool' 
| 'double' 
| 'int' 
| 'char' 
| 'container' 
| 'string' ;

arraytype : '[' ']' 
|/* epsilon */ ;

id : LETTER ;

dclend : /* epsilon */ 
| assign ;

assign : '<--' expr ;

expr : term exprend ;

term : comp termend ;

comp : factor compend ;

factor : '(' expr ')' 
| '!' '(' expr ')' 
| callid 
| callid '(' expr ')' 
| callid '(' ')' 
| numeric 
| string 
| functioncall 
| cast 
| 'LOW' 
| 'HIGH' 
| 'true' 
| 'false' ;

callid : id arrayiden 
| id ;

arrayiden : ARRAYCALL ;

ARRAYCALL : '[' NOTZERODIGIT ']' ;

numeric : plusminus DIGIT numericend ;

plusminus : /* epsilon */ 
| '-' 
| '+' ;

numericend : /* epsilon */ 
| '.' DIGIT ;

string : STRINGTOKEN ;

functioncall : 'call' id '(' callexpr ')' ;

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

function : 'function' id 'return' functionmidt ;

functionmidt : type functionend expr ';' 'end' 
| 'nothing' functionend 'nothing' ';' 'end' ;

functionend : 
'using' '(' params ')' 
'begin' 
stmts 
'return' ;

params : subparams 
|/* epsilon */ ;

subparams : type id subparamsend ;

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

from : 'from' expr 'to' expr 'step' callid assign 
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

STRINGTOKEN : '"'. * ? '"' ;

LETTER : [a-zA-Z]+ ;

DIGIT : [0-9]+ ;

NOTZERODIGIT : [1-9][0-9]* ;

COMMENT : '/' '*' . * ? '*' '/' -> skip ;



WS : [ \r\t\n]+ -> skip ;OTHER: ' ' -> skip;