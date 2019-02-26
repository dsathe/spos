%{
	#include <stdio.h>
	#include "scanner.h"
%}

digit [0-9]
int {digit}+
float {digit}+(.{digit}+)?(}((E|e){digit}+)?
char '.'
string ".*"
datatype int|float|char|String|double

%%
= {return ASSIGN;}
int {return INTDECL;}
float {return FLOATDECL;}
char {return STRINGDECL;}
String {return STRINGDECL;}
{int}	{return INT;}
{float}	{return FLOAT;}
{char}	{return CHAR;}
{string}	{return STRING;}
,	{return COMMA;}
;	{return SEMICOLON;}
[a_zA-Z][a-zA-z0-9]* {return ID;}
\n {return 0;}
. {}
%%

int yywrap(void)
{
	return 1;
}
