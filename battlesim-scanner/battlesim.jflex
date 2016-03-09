include java_cup.runtime.*;


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

StringCharacter     = [^\r\n\"\\]
IntegerLiteral      = 0 | [1-9][0-9]*
NumberLiteral       = [0-9]*\.[0-9]+ | {IntegerLiteral}
Identifier          = [a-zA-Z_] [a-zA-Z0-9_]*

%state STRING

%%

<YYINITIAL> {
    /* Keywords */
    "Boolean"               { return symbol(sym.BOOLEAN); }
    "If"                    { return symbol(sym.IF); }
    "While"                 { return symbol(sym.WHILE); }
    "Do"                    { return symbol(sym.DO); }
    "Foreach"               { return symbol(sym.FOREACH); }
    "Function"              { return symbol(sym.FUNCTION); }
    "End"                   { return symbol(sym.END); }
    "Begin"                 { return symbol(sym.BEGIN); }
    "Program"               { return symbol(sym.PROGRAM); }
    "For"                   { return symbol(sym.FOR); }
    "Type"                  { return symbol(sym.TYPE); }
    "as"                    { return symbol(sym.AS); }
    "Number"                { return symbol(sym.NUMBER); }
    "String"                { return symbol(sym.STRING); }
    "Declare"               { return symbol(sym.DECLARE); }
    "In"                    { return symbol(sym.IN); }
    "Return"                { return symbol(sym.RETURN); }
    "Else"                  { return symbol(sym.ELSE); }
    "List"                  { return symbol(sym.LIST); }
    "Include"               { return symbol(sym.INCLUDE); }
    "Define"                { return symbol(sym.DEFINE); }
    "Switch"                { return symbol(sym.SWITCH); }
    "NULL"                  { return symbol(sym.NULL_LITERAL); }
    "Case"                  { return symbol(sym.CASE); }
    "Default"               { return symbol(sym.DEFAULT); }

    /* Boolean literals */
    "true"                  { return symbol(sym.BOOLEAN_LITERAL, true); }
    "false"                 { return symbol(sym.BOOLEAN_LITERAL, false); }

    /* Seperators */
    "("                     { return symbol(sym.LPAREN); }
    ")"                     { return symbol(sym.RPAREN); }
    "["                     { return symbol(sym.LBRACE); }
    "]"                     { return symbol(sym.RBRACE); }
    ","                     { return symbol(sym.COMMA); }
    "."                     { return symbol(sym.DOT); }

    /* Operators */
    "="                     { return symbol(sym.EQ); }
    "++"                    { return symbol(sym.PLUSPLUS); }
    "--"                    { return symbol(sym.MINUSMINUS); }
    "+"                     { return symbol(sym.PLUS); }
    "-"                     { return symbol(sym.MINUS); }
    "/"                     { return symbol(sym.DIV); }
    "*"                     { return symbol(sym.MULT); }
    "%"                     { return symbol(sym.MOD); }
    "+="                    { return symbol(sym.PLUSEQ); }
    "-="                    { return symbol(sym.MINUSEQ); }
    "*="                    { return symbol(sym.MULTEQ); }
    "/="                    { return symbol(sym.DIVEQ); }
    "%="                    { return symbol(sym.MODEQ); }
    "AND"                   { return symbol(sym.AND); }
    "OR"                    { return symbol(sym.OR); }
    "NOT"                   { return symbol(sym.NOT); }
    "EQUALS"                { return symbol(sym.EQUALS); }
    "GREATERTHAN"           { return symbol(sym.GREATERTHAN); }
    "GREATERTHANEQUALS"     { return symbol(sym.GREATERTHANEQUALS); }
    "LESSTHAN"              { return symbol(sym.LESSTHAN); }
    "LESSTHANEQUALS"        { return symbol(sym.LESSTHANEQUALS); }

    /* string? */

    /* Numeric literals */
    {IntegerLiteral}        { return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); }
    {NumberLiteral}         { return symbol(sym.NUMBER_LITERAL, new Double(yytext())); }

    /* String literal */
    \"                      { yybegin(STRING); string.setLength(0); }

    /* Ignore these */
    {Comment}               { /* nothing happens here, maybe */ }
    {WhiteSpace}            { /* nothing happens here, maybe */ }


    /* Identifiers */
    {Identifier}            { return symbol(sym.IDENTIFIER, yytext())); }
}

<STRING> {
    \"                      { yybegin(YYINITIAL); return symbol(sym.STRING_LITERAL, string.toString()); }

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
<<EOF>>                     { return symbol(EOF); }