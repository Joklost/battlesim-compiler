package com.company;

import java_cup.runtime.Symbol;

/**
 * Created by joklost on 3/10/16.
 */
public class TokenStream {
    private Token nextToken;
    private Scanner scanner;

    public TokenStream(Scanner scanner) {
        this.scanner = scanner;
    }

    public int peek() {
        return nextToken.getType();
    }

    public Token advance() {
        Token ans = nextToken;
        nextToken = readToken();
        return ans;
    }

    private Token readToken() {
        try {
            Symbol sym = scanner.next_token();
            return new Token(sym.sym, (sym.value == null ? "" : sym.value.toString()));
        } catch (java.io.IOException e) {
            System.out.println("IO error scanning file.");
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }
        return null;
    }

}
