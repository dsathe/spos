%{
	#include <stdio.h>
	extern FILE *yyin;
	void yyerror(const char *s);
	int yylex();
	int line_no = 0; 
%}

%token INT STRING FLOAT CHAR ASSIGN STRINGDECL INTDECL FLOATDECL CHARDECL COMMA SEMICOLON ID SPACE

%start start
%%

start: STRINGDECL strvarlist SEMICOLON {printf("Valid string declaration %s\n",yytext)}
		| INTDECL intvarlist SEMICOLON {printf("Valid integer declaration %s\n",yytext)}
		| FLOATDECL floatvarlist SEMICOLON {printf("Valid float declaration %s\n",yytext)}
		| CHARDECL charvarlist SEMICOLON {printf("Valid float declaration %s\n",yytext)}
		;

		
		
		
