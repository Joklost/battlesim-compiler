package com.company;

import java.util.HashMap;

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

    public int getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
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
        EOL                 = 58,

        /* For iterators */
        DOWNTO              = 59, TO                  = 60,

        /* Extras (dem vi glemte) */
        THEN                = 61, VOID                = 62,
        LANGLE              = 63, RANGLE              = 64;

    private static HashMap<Integer, String> tokenNames;
    static {
        tokenNames = new HashMap<Integer, String>();

        /* Keywords */
        tokenNames.put(BOOLEAN, "BOOLEAN");
        tokenNames.put(IF, "IF");
        tokenNames.put(WHILE, "WHILE");
        tokenNames.put(DO, "DO");
        tokenNames.put(FOREACH, "FOREACH");
        tokenNames.put(FUNCTION, "FUNCTION");
        tokenNames.put(END, "END");
        tokenNames.put(BEGIN, "BEGIN");
        tokenNames.put(PROGRAM, "PROGRAM");
        tokenNames.put(FOR, "FOR");
        tokenNames.put(TYPE, "TYPE");
        tokenNames.put(AS, "AS");
        tokenNames.put(NUMBER, "NUMBER");
        tokenNames.put(STRING, "STRING");
        tokenNames.put(DECLARE, "DECLARE");
        tokenNames.put(IN, "IN");
        tokenNames.put(RETURN, "RETURN");
        tokenNames.put(ELSE, "ELSE");
        tokenNames.put(LIST, "LIST");
        tokenNames.put(INCLUDE, "INCLUDE");
        tokenNames.put(DEFINE, "DEFINE");
        tokenNames.put(SWITCH, "SWITCH");
        tokenNames.put(NULL_LITERAL, "NULL_LITERAL");
        tokenNames.put(CASE, "CASE");
        tokenNames.put(DEFAULT, "DEFAULT");

        /* Seperators */
        tokenNames.put(BOOLEAN_LITERAL, "BOOLEAN_LITERAL");
        tokenNames.put(LPAREN, "LPAREN");
        tokenNames.put(RPAREN, "RPAREN");
        tokenNames.put(LBRACE, "LBRACE");
        tokenNames.put(RBRACE, "RBRACE");
        tokenNames.put(COMMA, "COMMA");
        tokenNames.put(DOT, "DOT");

        /* Operators */
        tokenNames.put(EQ, "EQ");
        tokenNames.put(PLUSPLUS, "PLUSPLUS");
        tokenNames.put(MINUSMINUS, "MINUSMINUS");
        tokenNames.put(PLUS, "PLUS");
        tokenNames.put(MINUS, "MINUS");
        tokenNames.put(DIV, "DIV");
        tokenNames.put(MULT, "MULT");
        tokenNames.put(MOD, "MOD");
        tokenNames.put(PLUSEQ, "PLUSEQ");
        tokenNames.put(MINUSEQ, "MINUSEQ");
        tokenNames.put(MULTEQ, "MULTEQ");
        tokenNames.put(DIVEQ, "DIVEQ");
        tokenNames.put(MODEQ, "MODEQ");
        tokenNames.put(AND, "AND");
        tokenNames.put(OR, "OR");
        tokenNames.put(NOT, "NOT");
        tokenNames.put(EQUALS, "EQUALS");
        tokenNames.put(GREATERTHAN, "GREATERTHAN");
        tokenNames.put(GREATERTHANEQUALS, "GREATERTHANEQUALS");
        tokenNames.put(LESSTHAN, "LESSTHAN");
        tokenNames.put(LESSTHANEQUALS, "LESSTHANEQUALS");

        /* Literals */
        tokenNames.put(INTEGER_LITERAL, "INTEGER_LITERAL");
        tokenNames.put(NUMBER_LITERAL, "NUMBER_LITERAL");
        tokenNames.put(STRING_LITERAL, "STRING_LITERAL");

        /* Other */
        tokenNames.put(IDENTIFIER, "IDENTIFIER");
        tokenNames.put(EOF, "EOF");
        tokenNames.put(EOL, "EOL");

        tokenNames.put(DOWNTO, "DOWNTO");
        tokenNames.put(TO, "TO");
        tokenNames.put(VOID, "VOID");
        tokenNames.put(LANGLE, "LANGLE");
        tokenNames.put(RANGLE, "RANGLE");

    }

    public static String getTokenName(int token) {
        return Token.tokenNames.get(token);
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

}
