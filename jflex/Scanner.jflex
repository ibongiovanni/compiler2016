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

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
	/* Return the token SEMI declared in the class sym that was found. */
    ";"                { return symbol(";", sym.SEMI); }
    ","                { return symbol(",", sym.COMA); }
    "."                { return symbol(".", sym.DOT); }
   
    /* Math Operators */
    "+"                { return symbol("+", sym.PLUS); }
    "-"                { return symbol("-", sym.MINUS); }
    "*"                { return symbol("*", sym.TIMES); }
    "/"                { return symbol("/", sym.DIVIDE); }
    "%"                { return symbol("%", sym.MOD); }
    
    /* Assign Operators */
    "="                { return symbol("=", sym.ASSIGN); }
    "+="               { return symbol("+=", sym.PLUSASSIGN); }
    "-="               { return symbol("-=", sym.LESSASSIGN); }
    
    /* Boolean operators */
	"=="                { return symbol("==", sym.EQUAL); }
    "||"                { return symbol("||", sym.OR); }
    "&&"                { return symbol("&&", sym.AND); }
    "<"                	{ return symbol("<", sym.LT); }
    ">"                	{ return symbol(">", sym.GT); }
    "<="                { return symbol("<=", sym.LOET); }
    ">="                { return symbol(">=", sym.GOET); }
    "!"                	{ return symbol("!", sym.NOT); }
    "!="                { return symbol("!=", sym.NOTEQ); }

    "("                { return symbol("(", sym.LPAREN); }
    ")"                { return symbol(")", sym.RPAREN); }
    "{"                { return symbol("{", sym.LBRACE); }
    "}"                { return symbol("}", sym.RBRACE); }
    "["                { return symbol("[", sym.LBRACK); }
  	"]"                { return symbol("]", sym.RBRACK); }

    /* Boolean Literals */
    "false"				{ return symbol("false", sym.BOOL_LIT, false); }
    "true"				{ return symbol("true", sym.BOOL_LIT, true); }

    /* Keywords */ /* bool break class continue else false float for if integer return true void while extern */
    "bool"				{ return symbol("bool", sym.BOOL); }
	"break"				{ return symbol("break", sym.BREAK); }
	"class"				{ return symbol("class", sym.CLASS); }
	"continue"			{ return symbol("continue", sym.CONTINUE); }
	"else"				{ return symbol("else", sym.ELSE); }	
	"for"				{ return symbol("for", sym.FOR); }
	"if"				{ return symbol("if", sym.IF); }
	
	"return"			{ return symbol("return", sym.RETURN); }
	"void"				{ return symbol("void", sym.VOID); }
	"while"				{ return symbol("while", sym.WHILE); }
	"extern"			{ return symbol("extern", sym.EXTERN); }

	"integer"			{ return symbol("integer", sym.INTEGER_TYPE); }
	"float"				{ return symbol("float", sym.FLOAT_TYPE); }


    {IntegerLiteral} 	{ return symbol("Integer: "+yytext(), sym.INT_LIT, new Integer(yytext())); }
   
   	{IdentifierLiteral}	{ return symbol("Identifier: "+yytext(), sym.ID, yytext());}

   	{WhiteSpace}		{ /* Do nothing*/ }

}