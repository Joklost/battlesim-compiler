package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class UnMinusExpr extends Expression {
    public Expression expression;

    public UnMinusExpr(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
