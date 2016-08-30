package Example;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
%%
%cup
%line
%column
%char
%class Scanner
%{
	public Scanner(java.io.InputStream r, ComplexSymbolFactory sf){
		this(r);
		this.sf=sf;
	}
	public Symbol symbol(String plaintext,int code){
	    return sf.newSymbol(plaintext,code,new Location("",yyline+1, yycolumn +1,yychar), new Location("",yyline+1,yycolumn+yylength(),yychar));
	}
	public Symbol symbol(String plaintext,int code, Object value){
	    return sf.newSymbol(plaintext,code,new Location("",yyline+1, yycolumn +1,yychar), new Location("",yyline+1,yycolumn+yylength(),yychar),value);
	}
	private ComplexSymbolFactory sf;

	/* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}



/* Macros */

LineTerminator = \r | \n | \r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

IntegerLiteral = 0 | [1-9][0-9]*

IdentifierLiteral = [A-Za-z_][A-Za-z_0-9]*

%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
	/* Return the token SEMI declared in the class sym that was found. */
    ";"                { System.out.print(" + "); return symbol(sym.SEMI); }
   
    /* Math Operators */
    "+"                { System.out.print(" + "); return symbol(sym.PLUS); }
    "-"                { System.out.print(" - "); return symbol(sym.MINUS); }
    "*"                { System.out.print(" * "); return symbol(sym.TIMES); }
    "/"                { System.out.print(" / "); return symbol(sym.DIVIDE); }


    "("                { System.out.print(" ( "); return symbol(sym.LPAREN); }
    ")"                { System.out.print(" ) "); return symbol(sym.RPAREN); }
    "{"                { System.out.print(" ( "); return symbol(sym.LBRACE); }
    "}"                { System.out.print(" ) "); return symbol(sym.RBRACE); }

    /* Boolean Literals */
    "false"				{ System.out.print(" false "); return symbol(sym.BOOL_LIT, false); }
    "true"				{ System.out.print(" true "); return symbol(sym.BOOL_LIT, true); }

    /* Keywords */ /* bool break class continue else false float for if integer return true void while extern */
    "bool"				{ System.out.print(" bool "); return symbol(sym.BOOL); }
	"break"				{ System.out.print(" break "); return symbol(sym.BREAK); }
	"class"				{ System.out.print(" class "); return symbol(sym.CLASS); }
	"continue"				{ System.out.print(" continue "); return symbol(sym.CONTINUE); }
	"else"				{ System.out.print(" else "); return symbol(sym.ELSE); }	
	"float"				{ System.out.print(" float "); return symbol(sym.FLOAT); }
	"for"				{ System.out.print(" for "); return symbol(sym.FOR); }
	"if"				{ System.out.print(" if "); return symbol(sym.IF); }
	"integer"				{ System.out.print(" integer "); return symbol(sym.INTEGER); }
	"return"				{ System.out.print(" return "); return symbol(sym.RETURN); }
	"void"				{ System.out.print(" void "); return symbol(sym.VOID); }
	"while"				{ System.out.print(" while "); return symbol(sym.WHILE); }
	"extern"				{ System.out.print(" extern "); return symbol(sym.EXTERN); }

    {IntegerLiteral} 	{ System.out.print(yytext()); return symbol(sym.NUMBER, new Integer(yytext())); }
   
   	{IdentifierLiteral}	{ System.out.print(yytext()); return symbol(sym.ID, yytext());}

   	{WhiteSpace}		{ /* Do nothing*/ }

}