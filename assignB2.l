%{
	/* Program  to generate Java Tokens*/
%}

digit [0-9]
integer {digit}*
floatingPoint {digit}*\.{digit}*(E|e)?{digit}+
identifier [_a-zA-Z]+[_a-zA-Z0-9]*
unaryOperator	--|\+\+
binaryOperator	\+|-|\*|\/|%|<<|>>|>>>
comparisionOperator <|>|>=|>=|==|!=|instance\sof
bitwiseOperator &|\||\^
logicalOperator &&|\|\|
assignmentOperator \+=|-=|\*=|\/=|%=|<<=|>>=|>>>=|&=|\|=|\^=|=
loopKeywords for|while|do
conditionalKeywords if|else\sif|else
exceptionKeywords try|catch|finally
datatypes int|float|char|boolean|void
nullKeyword null
booleanKeywords true|false
accessSpecifier	public|private|protected
classTokens	class|static|final|abstract|implements|extends|interface|new
importToken	^(import)
string "[a-zA-Z0-9\?\*!@#$&]*"
comment	(\/\/.*|\/\*(.|\n)*\*\/)

%%
{comment}	 {printf("(Comment:-%s)\n",yytext);}
{nullKeyword}	{printf("(Null:%s)\n", yytext);}
{importToken}	{printf("(Import token:%s\n)", yytext);}
{unaryOperator}	{printf("(Unary Operator:%s\n)", yytext);}
{binaryOperator}	{printf("(Binary Operator:%s)\n", yytext);}
{comparisionOperator}	{printf("(Comparision Operator:%s)\n", yytext);}
{bitwiseOperator}	{printf("(Bitwise Operator:%s)\n", yytext);}
{logicalOperator}	{printf("(Logical Operator:%s)\n", yytext);}
{assignmentOperator}	{printf("(Assignment Operator:%s)\n", yytext);} 
{booleanKeywords}	{printf("(Boolean:%s)\n", yytext);}
{datatypes}	{printf("(Datatype:%s)\n", yytext);}
{loopKeywords}	{printf("(Loop Keyword:%s)\n", yytext);}
{conditionalKeywords}	{printf("(Conditional:%s)\n", yytext);}
{exceptionKeywords}	{printf("(Exception keyword:%s)\n", yytext);}
{accessSpecifier}	{printf("(Access Specifier:%s)\n", yytext);}
{classTokens}	{printf("(Class Token:%s)\n", yytext);}
{identifier}	{printf("(Identifier:%s)\n", yytext);}
{integer}	{printf("(Integer:%s)\n", yytext);}
{floatingPoint}	{printf("(Float:%s)\n", yytext);}
{string}	{printf("(String:%s)\n", yytext);}

%%

int yywrap()
{
	return 1;
}

int main()
{
	extern FILE *yyin;
	yyin=fopen("myfile.java","r");
	yylex();
	return 0;
}	

