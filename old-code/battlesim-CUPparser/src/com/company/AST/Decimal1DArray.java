package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class Decimal1DArray extends TypeIdentifier {
    public Expression expression;

    public Decimal1DArray(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
