package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class Boolean1DArray extends TypeIdentifier {
    public Expression expression;

    public Boolean1DArray(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
