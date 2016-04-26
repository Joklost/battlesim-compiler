package com.company.SyntaxAnalysis;
import java_cup.runtime.*;


%%
%class Scanner
%public
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

/* Code injection */
CodeInjection       = "%``" {InputCharacter}* {LineTerminator}?

/* StringLiteral       = "([^"\\]*(\\.[^"\\]*)*)" */
/* BooleanLiteral      = (true | false) */

/* ListDeclaration     = (List< {Identifier} >) */

StringCharacter     = [^\r\n\"\\]
IntegerLiteral      = 0 | [1-9][0-9]*
DecimalLiteral      = [0-9]*\.[0-9]+ /*| {IntegerLiteral}*/
Identifier          = [a-zA-Z_] [a-zA-Z0-9_]*

%state STRING

%%

<YYINITIAL> {
    /* Keywords */
    "Boolean"               { return symbol(Sym.BOOLEAN); }
    "If"                    { return symbol(Sym.IF); }
    "While"                 { return symbol(Sym.WHILE); }
    "Do"                    { return symbol(Sym.DO); }
    "Foreach"               { return symbol(Sym.FOREACH); }
    "Function"              { return symbol(Sym.FUNCTION); }
    "End"                   { return symbol(Sym.END); }
    "Begin"                 { return symbol(Sym.BEGIN); }
    "Program"               { return symbol(Sym.PROGRAM); }
    "For"                   { return symbol(Sym.FOR); }
    "Type"                  { return symbol(Sym.TYPE); }
    "Integer"               { return symbol(Sym.INTEGER); }
    "as"                    { return symbol(Sym.AS); }
    "Decimal"               { return symbol(Sym.DECIMAL); }
    "String"                { return symbol(Sym.STRING); }
    "Declare"               { return symbol(Sym.DECLARE); }
    "in"                    { return symbol(Sym.IN); }
    "Return"                { return symbol(Sym.RETURN); }
    "Else"                  { return symbol(Sym.ELSE); }
    "List"                  { return symbol(Sym.LIST); }
    "of"                    { return symbol(Sym.OF); }
    /*"Include"               { return symbol(Sym.INCLUDE); }*/
    /*"Define"                { return symbol(Sym.DEFINE); }*/
    "Switch"                { return symbol(Sym.SWITCH); }
    "NULL"                  { return symbol(Sym.NULL_LITERAL); }
    "Case"                  { return symbol(Sym.CASE); }
    "Default"               { return symbol(Sym.DEFAULT); }
    "DownTo"                { return symbol(Sym.DOWNTO); }
    "To"                    { return symbol(Sym.TO); }
    "Then"                  { return symbol(Sym.THEN); }
    "Void"                  { return symbol(Sym.VOID); }
/*
    "Terrain"               { return symbol(Sym.TERRAIN); }
    "Group"                 { return symbol(Sym.GROUP); }
    "Platoon"               { return symbol(Sym.PLATOON); }
    "Force"                 { return symbol(Sym.FORCE); }
    "Coord"                 { return symbol(Sym.COORD); }
    "Soldier"               { return symbol(Sym.SOLDIER); }
    "Barrier"               { return symbol(Sym.BARRIER); }
    "Vector"                { return symbol(Sym.VECTOR); }
*/
    "Simulation"            { return symbol(Sym.SIMULATION); }
    "Step"                  { return symbol(Sym.STEP); }
    "Interrupts"            { return symbol(Sym.INTERRUPTS); }
    "Declarations"          { return symbol(Sym.DECLARATIONS); }
    "Simulations"           { return symbol(Sym.SIMULATIONS); }

    /* Boolean literals */
    "true"                  { return symbol(Sym.BOOLEAN_LITERAL, true); }
    "false"                 { return symbol(Sym.BOOLEAN_LITERAL, false); }

    /* Seperators */
    "("                     { return symbol(Sym.LPAREN); }
    ")"                     { return symbol(Sym.RPAREN); }
    "["                     { return symbol(Sym.LBRACE); }
    "]"                     { return symbol(Sym.RBRACE); }
    ","                     { return symbol(Sym.COMMA); }
    "."                     { return symbol(Sym.DOT); }

    /* Operators */
    "="                     { return symbol(Sym.EQ); }
    "++"                    { return symbol(Sym.PLUSPLUS); }
    "--"                    { return symbol(Sym.MINUSMINUS); }
    "+"                     { return symbol(Sym.PLUS); }
    "-"                     { return symbol(Sym.MINUS); }
    "/"                     { return symbol(Sym.DIV); }
    "*"                     { return symbol(Sym.MULT); }
    "%"                     { return symbol(Sym.MOD); }
    "+="                    { return symbol(Sym.PLUSEQ); }
    "-="                    { return symbol(Sym.MINUSEQ); }
    "*="                    { return symbol(Sym.MULTEQ); }
    "/="                    { return symbol(Sym.DIVEQ); }
    "%="                    { return symbol(Sym.MODEQ); }
    "AND"                   { return symbol(Sym.AND); }
    "OR"                    { return symbol(Sym.OR); }
    "NOT"                   { return symbol(Sym.NOT); }
    "=="                    { return symbol(Sym.EQUALS); }
    ">"                     { return symbol(Sym.GREATERTHAN); }
    ">="                    { return symbol(Sym.GREATERTHANEQUALS); }
    "<"                     { return symbol(Sym.LESSTHAN); }
    "<="                    { return symbol(Sym.LESSTHANEQUALS); }

    /* Numeric literals */
    {IntegerLiteral}        { return symbol(Sym.INTEGER_LITERAL, new Integer(yytext())); }
    {DecimalLiteral}        { return symbol(Sym.DECIMAL_LITERAL, new Double(yytext())); }

    /* String literal */
    \"                      { yybegin(STRING); string.setLength(0); }

    {LineTerminator}        { return symbol(Sym.EOL); }

    /* Ignore these */
    {Comment}               { return symbol(Sym.EOL); }
    {WhiteSpace}            { /* nothing happens here, maybe */ }


    /* Identifiers */
    {Identifier}            { return symbol(Sym.IDENTIFIER, yytext()); }

    {CodeInjection}         { return symbol(Sym.JAVASTRING, yytext()); }

}

<STRING> {
    \"                      { yybegin(YYINITIAL); return symbol(Sym.STRING_LITERAL, string.toString()); }

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
<<EOF>>                     { return symbol(Sym.EOF); }
