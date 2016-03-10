package com.company;

/**
 * Created by joklost on 3/10/16.
 */
public class Parser {
    private TokenStream ts;

    public Parser(Scanner scanner) {
        this.ts = new TokenStream(scanner);
    }

    private void expect(int type) {
        Token t = ts.advance();
        if (t.getType() != type) {
            error("Expected type " + Token.getTokenName(type) + " but received type " + Token.getTokenName(t.getType()));
        }
    }

    private void error(String message) {
        throw new Error(message);
    }

}
