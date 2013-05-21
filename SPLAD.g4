grammar SPLAD; 
options {language = Java; }

program : roots ;

roots : /* epsilon */ 
| root roots ;

root : dcl ';' 
| assign ';' 
| function 
| COMMENT 
| drinkdcl ;

dcl : type assign ;

drinkdcl : 'drink' id 'is' 'begin' drinkstmts 'end' 
| 'drink' id 'as' id 'but' 'begin' changedrinkstmts 'end' ;

drinkstmts : drinkstmt drinkstmtsend ;

drinkstmt : 'add' numeric 'of' id ;

drinkstmtsend : ';' drinkstmts 
| ';' ;

changedrinkstmts : changedrinkstmt changedrinkstmtsend ;

changedrinkstmt : drinkstmt 
| 'remove' id ;

changedrinkstmtsend : ';' changedrinkstmts 
| ';' ;

type : constant primitivetype 
| specialtype ;

constant : 'const' 
| /* epsilon */ ;

primitivetype : 'bool' 
| 'double' 
| 'int' 
| 'char' 
| 'string' ;

specialtype : 'drink' 
| 'container' ;

id : LETTER ;

assign : callid assignend ;

assignend : '<--' expr 
| /* epsilon */ ;

expr : term exprend ;

term : comp termend ;

comp : addsub compend ;

addsub : muldiv addsubend ;

muldiv : factor muldivend ;

factor : '(' expr ')' 
| '!' '(' expr ')' 
| callid 
| numeric 
| 'A' DIGIT 
| string 
| functioncall 
| cast 
| 'LOW' 
| 'HIGH' 
| 'true' 
| 'false' 
| 'INPUT' 
| 'OUTPUT' ;

callid : id arrayidend ;

arrayidend : arraycall '[' expr ']' 
| /* epsilon */ ;

arraycall : '[' expr ']' arraycall 
| '[' ']' arraycall 
| /* epsilon */ ;

numeric : plusminusorempty DIGIT numericend ;

addsubend : plusminus addsub 
| /* epsilon */ ;

muldivend : timesdivide muldiv 
| /* epsilon */ ;

timesdivide : '*' 
| '/' ;

plusminusorempty : /* epsilon */ 
| plusminus ;

plusminus :'-' 
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

termend : termsymbol term 
|/* epsilon */ ;

termsymbol : 'AND' ;

exprend : exprsymbol expr 
|/* epsilon */ ;

exprsymbol : 'OR' ;

function : 'function' id 'return' functionmid ;

functionmid : type functionend expr ';' 'end' 
| 'nothing' functionend 'nothing' ';' 'end' ;

functionend : 
'using' '(' params ')' 
'begin' 
stmts 
'return' ;

params : subparams 
|/* epsilon */ ;

subparams : type callid subparamsend ;

subparamsend : ',' subparams 
|/* epsilon */ ;

stmts : /* epsilon */ 
| stmt stmts ;

stmt : assign ';' 
| nontermif 
| nontermwhile 
| from 
| dcl ';' 
| functioncall ';' 
| nontermswitch 
| COMMENT ;

nontermif : 'if''(' expr ')' 
block 
endif ;

endif : 
'else' nontermelse 
|/* epsilon */ ;

nontermelse : nontermif 
| block ;

nontermwhile : 'while' '(' expr ')' 
block ;

from : 'from' assign 'to' expr 'step' plusminusorempty DIGIT 
block ;

block : 
'begin' 
stmts 
'end' ;

nontermswitch : 'switch' '(' expr ')' 
'begin' 
cases 
'end' ;

cases : 'case' expr':' 
stmts 'break' ';'
endcase ;

endcase : cases 
| 'default'':' 
stmts 
'break' ';' ;




STRINGTOKEN : '"'. * ? '"' ;

LETTER : [a-zA-Z]+ ;

DIGIT : [0-9]+ ;

NOTZERODIGIT : [1-9][0-9]* ;

COMMENT : '/' '*' . * ? '*' '/' -> skip ;



WS : [ \r\t\n]+ -> skip ;OTHER: ' ' -> skip;