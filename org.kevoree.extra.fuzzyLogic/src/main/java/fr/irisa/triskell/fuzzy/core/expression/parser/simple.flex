/* A simple jflex scanner to read in lines containing
 * simple expressions with integers, assignments or
 * print statements. See test.txt for an example
 * input file.
 */

%%

%{
   // Not used
%} 

%class Tokenizer
%line
%column
%full
%function getNextToken
%type Token

ALPHA=[A-Za-z]

DIGIT=[0-9]

NEWLINE=\r|\n|\r\n

WHITE_SPACE_CHAR=[\n\r\ \t\b\012]

Identifier = {ALPHA}({ALPHA}|{DIGIT}|_)*

Label = "'" {Identifier} "'"


%% 

<YYINITIAL> {

	"(" 		{ return (new Token(Token.OPEN_BRACKET, yytext(), yyline ,yycolumn)); }
	")" 		{ return (new Token(Token.CLOSE_BRACKET, yytext(), yyline ,yycolumn)); }

	"and" 		{ return (new Token(Token.AND_KW, yytext(), yyline ,yycolumn)); }
  	"or"  		{ return (new Token(Token.OR_KW, yytext(), yyline ,yycolumn)); }
  	"=>"		{ return (new Token(Token.IMPLIES_KW, yytext(), yyline ,yycolumn));}
  	"is"		{ return (new Token(Token.IS_KW, yytext(), yyline ,yycolumn));}
  	"not"		{ return (new Token(Token.NOT_KW, yytext(), yyline ,yycolumn));}
  	"very"		{ return (new Token(Token.VERY_KW, yytext(), yyline ,yycolumn));}
  	"moderatly"	{ return (new Token(Token.MODERATLY_KW, yytext(), yyline ,yycolumn));}
  	"slightly"	{ return (new Token(Token.SLIGHTLY_KW, yytext(), yyline ,yycolumn));}
  		
	{Identifier} { return (new Token(Token.IDENTIFIER, yytext(), yyline ,yycolumn)); }  
  	{Label} { return (new Token(Token.LABEL, yytext(), yyline ,yycolumn)); }

	{NEWLINE}+ { }			// Completely ignore newlines
  	{WHITE_SPACE_CHAR}+ { }		// and whitespace in the input
}

<<EOF>> {
	return new Token(Token.EOF, "eof", -1, -1);
}

. {
  System.err.println("Illegal character: \"" + yytext() + "\" at " + yycolumn);
}
