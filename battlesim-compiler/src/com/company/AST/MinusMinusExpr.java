package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class MinusMinusExpr extends Expression {
    public Expression expression;

    public MinusMinusExpr(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
