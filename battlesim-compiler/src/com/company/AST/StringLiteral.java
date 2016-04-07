package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class StringLiteral extends StdLiteral {
    public String string;

    public StringLiteral(String s, int ln) {
        super(ln);
        this.string = s;
    }
}
