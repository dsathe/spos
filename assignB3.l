%option noyywrap
%{
#include <stdio.h>
int wordcount=0;
int 
%}
%%
([a-zA-Z0-9])*    {wordcount++;} /* Rule for counting  
                          number of words*/
  
"\n" {printf("No. of words in a given sentence are:- %d\n", wordcount); i = wordcount;} 
%%
int main()
{
	yylex();
}
