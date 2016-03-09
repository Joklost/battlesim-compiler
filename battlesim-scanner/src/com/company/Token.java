package com.company;

/**
 * Created by joklost on 3/9/16.
 */
public class Token {

    private int type;
    private String value;

    public Token(int type) {
        this(type, "");
    }

    public Token(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return Token.getTokenName(type) + (value == "" ? "" : ": " + value);
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    public final static int
            /*  Keywords */
        BOOLEAN             = 0,  IF                  = 1,
        WHILE               = 2,  DO                  = 3,
        FOREACH             = 4,  FUNCTION            = 5,
        END                 = 6,  BEGIN               = 7,
        PROGRAM             = 8,  FOR                 = 9,
        TYPE                = 10, AS                  = 11,
        NUMBER              = 12, STRING              = 13,
        DECLARE             = 14, IN                  = 15,
        RETURN              = 16, ELSE                = 17,
        LIST                = 18, INCLUDE             = 19,
        DEFINE              = 20, SWITCH              = 21,
        NULL_LITERAL        = 22, CASE                = 23,
        DEFAULT             = 24,

            /* Seperators */
        BOOLEAN_LITERAL     = 25, LPAREN              = 26,
        RPAREN              = 27, LBRACE              = 28,
        RBRACE              = 29, COMMA               = 30,
        DOT                 = 31,

            /* Operators */
        EQ                  = 32, PLUSPLUS            = 33,
        MINUSMINUS          = 34, PLUS                = 35,
        MINUS               = 36, DIV                 = 37,
        MULT                = 38, MOD                 = 39,
        PLUSEQ              = 40, MINUSEQ             = 41,
        MULTEQ              = 42, DIVEQ               = 43,
        MODEQ               = 44, AND                 = 45,
        OR                  = 46, NOT                 = 47,
        EQUALS              = 48, GREATERTHAN         = 49,
        GREATERTHANEQUALS   = 50, LESSTHAN            = 51,
        LESSTHANEQUALS      = 52,

            /* Literals */
        INTEGER_LITERAL     = 53, NUMBER_LITERAL      = 54,
        STRING_LITERAL      = 55,

            /* Other */
        IDENTIFIER          = 56, EOF                 = 57,
        NEWLINE             = 58;

    private static final String[] tokenNames = new String[] {
            /*  Keywords */
        "BOOLEAN", "IF", "WHILE", "DO", "FOREACH", "FUNCTION",
        "END", "BEGIN", "PROGRAM", "FOR", "TYPE", "AS", "NUMBER",
        "STRING", "DECLARE", "IN", "RETURN", "ELSE", "LIST",
        "INCLUDE", "DEFINE", "SWITCH", "NULL_LITERAL", "CASE",
        "DEFAULT",

            /* Seperators */
        "BOOLEAN_LITERAL", "LPAREN", "RPAREN", "LBRACE",
        "RBRACE", "COMMA", "DOT",

            /* Operators */
        "EQ", "PLUSPLUS", "MINUSMINUS", "PLUS", "MINUS", "DIV",
        "MULT", "MOD", "PLUSEQ", "MINUSEQ", "MULTEQ", "DIVEQ",
        "MODEQ", "AND", "OR", "NOT", "EQUALS", "GREATERTHAN",
        "GREATERTHANEQUALS", "LESSTHAN", "LESSTHANEQUALS",

            /* Literals */
        "INTEGER_LITERAL", "NUMBER_LITERAL", "STRING_LITERAL",

            /* Other */
        "IDENTIFIER", "EOF", "NEWLINE"
    };

    public static String getTokenName(int token) {
        return Token.tokenNames[token];
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

}
