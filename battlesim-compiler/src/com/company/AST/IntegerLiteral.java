package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class IntegerLiteral extends StdLiteral {
    public Integer integer;

    public IntegerLiteral(Integer i, int ln) {
        super(ln);
        this.integer = i;
    }
}
