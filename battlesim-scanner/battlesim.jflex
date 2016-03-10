package com.company;
import java_cup.runtime.*;

%%
%class Scanner
%unicode
%cup
%line
%column

%{
      StringBuffer string = new StringBuffer();

      private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
      }
      private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
      }

      /* This is required, as ZZAtEOF is private */
      public boolean getZZAtEOF() {
        return zzAtEOF;
      }
%}

LineTerminator      = \r|\n|\r\n
InputCharacter      = [^\r\n]
WhiteSpace          = {LineTerminator} | [ \t\f]

/* Comments */
Comment             = {MultiLineComment} | {EndOfLineComment}
MultiLineComment    = "/*" [^*] ~"*/" | "/*" "*"+ "/"   /* Traditional Comment like this one */
EndOfLineComment    = "//" {InputCharacter}* {LineTerminator}?
/* Comments */

/* StringLiteral       = "([^"\\]*(\\.[^"\\]*)*)" */
/* BooleanLiteral      = (true | false) */

ListDeclaration     = (List< {Identifier} >)

StringCharacter     = [^\r\n\"\\]
IntegerLiteral      = 0 | [1-9][0-9]*
NumberLiteral       = [0-9]*\.[0-9]+ | {IntegerLiteral}
Identifier          = [a-zA-Z_] [a-zA-Z0-9_]*

%state STRING

%%

<YYINITIAL> {
    /* Keywords */
    "Boolean"               { return symbol(Token.BOOLEAN); }
    "If"                    { return symbol(Token.IF); }
    "While"                 { return symbol(Token.WHILE); }
    "Do"                    { return symbol(Token.DO); }
    "Foreach"               { return symbol(Token.FOREACH); }
    "Function"              { return symbol(Token.FUNCTION); }
    "End"                   { return symbol(Token.END); }
    "Begin"                 { return symbol(Token.BEGIN); }
    "Program"               { return symbol(Token.PROGRAM); }
    "For"                   { return symbol(Token.FOR); }
    "Type"                  { return symbol(Token.TYPE); }
    "as"                    { return symbol(Token.AS); }
    "Number"                { return symbol(Token.NUMBER); }
    "String"                { return symbol(Token.STRING); }
    "Declare"               { return symbol(Token.DECLARE); }
    "In"                    { return symbol(Token.IN); }
    "Return"                { return symbol(Token.RETURN); }
    "Else"                  { return symbol(Token.ELSE); }
    {ListDeclaration}       { return symbol(Token.LIST, yytext()); }
    "Include"               { return symbol(Token.INCLUDE); }
    "Define"                { return symbol(Token.DEFINE); }
    "Switch"                { return symbol(Token.SWITCH); }
    "NULL"                  { return symbol(Token.NULL_LITERAL); }
    "Case"                  { return symbol(Token.CASE); }
    "Default"               { return symbol(Token.DEFAULT); }
    "DownTo"                { return symbol(Token.DOWNTO); }
    "To"                    { return symbol(Token.TO); }

    /* Boolean literals */
    "true"                  { return symbol(Token.BOOLEAN_LITERAL, true); }
    "false"                 { return symbol(Token.BOOLEAN_LITERAL, false); }

    /* Seperators */
    "("                     { return symbol(Token.LPAREN); }
    ")"                     { return symbol(Token.RPAREN); }
    "["                     { return symbol(Token.LBRACE); }
    "]"                     { return symbol(Token.RBRACE); }
    ","                     { return symbol(Token.COMMA); }
    "."                     { return symbol(Token.DOT); }

    /* Operators */
    "="                     { return symbol(Token.EQ); }
    "++"                    { return symbol(Token.PLUSPLUS); }
    "--"                    { return symbol(Token.MINUSMINUS); }
    "+"                     { return symbol(Token.PLUS); }
    "-"                     { return symbol(Token.MINUS); }
    "/"                     { return symbol(Token.DIV); }
    "*"                     { return symbol(Token.MULT); }
    "%"                     { return symbol(Token.MOD); }
    "+="                    { return symbol(Token.PLUSEQ); }
    "-="                    { return symbol(Token.MINUSEQ); }
    "*="                    { return symbol(Token.MULTEQ); }
    "/="                    { return symbol(Token.DIVEQ); }
    "%="                    { return symbol(Token.MODEQ); }
    "AND"                   { return symbol(Token.AND); }
    "OR"                    { return symbol(Token.OR); }
    "NOT"                   { return symbol(Token.NOT); }
    "EQUALS"                { return symbol(Token.EQUALS); }
    "GREATERTHAN"           { return symbol(Token.GREATERTHAN); }
    "GREATERTHANEQUALS"     { return symbol(Token.GREATERTHANEQUALS); }
    "LESSTHAN"              { return symbol(Token.LESSTHAN); }
    "LESSTHANEQUALS"        { return symbol(Token.LESSTHANEQUALS); }

    /* Numeric literals */
    {IntegerLiteral}        { return symbol(Token.INTEGER_LITERAL, new Integer(yytext())); }
    {NumberLiteral}         { return symbol(Token.NUMBER_LITERAL, new Double(yytext())); }

    /* String literal */
    \"                      { yybegin(STRING); string.setLength(0); }

    /* Is used if a NEWLINE token is necessary
    {LineTerminator}        { return symbol(Token.NEWLINE); }
    */

    /* Ignore these */
    {Comment}               { /* nothing happens here, maybe */ }
    {WhiteSpace}            { /* nothing happens here, maybe */ }


    /* Identifiers */
    {Identifier}            { return symbol(Token.IDENTIFIER, yytext()); }

}

<STRING> {
    \"                      { yybegin(YYINITIAL); return symbol(Token.STRING_LITERAL, string.toString()); }

    {StringCharacter}+      { string.append( yytext() ); }

    /* escape sequences */
    "\\b"                   { string.append( '\b' ); }
    "\\t"                   { string.append( '\t' ); }
    "\\n"                   { string.append( '\n' ); }
    "\\f"                   { string.append( '\f' ); }
    "\\r"                   { string.append( '\r' ); }
    "\\\""                  { string.append( '\"' ); }
    "\\'"                   { string.append( '\'' ); }
    "\\\\"                  { string.append( '\\' ); }

    /* error cases */
    \\.                     { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
    {LineTerminator}        { throw new RuntimeException("Unterminated string at end of line"); }
}

/* error fallback */
[^]                         { throw new RuntimeException("Illegal character \""+yytext()+"\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                     { return symbol(Token.EOF); }
