package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class Vector1DArray extends TypeIdentifier {
    public Expression expression;

    public Vector1DArray(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
