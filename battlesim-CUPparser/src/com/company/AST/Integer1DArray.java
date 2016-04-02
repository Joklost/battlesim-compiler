package com.company.AST;

/**
 * Created by joklost on 02-04-16.
 */
public class Integer1DArray extends TypeIdentifier {
    public Expression expression;

    public Integer1DArray(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
