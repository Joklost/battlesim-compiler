package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class PlusPlusExpr extends Expression {
    public Expression expression;

    public PlusPlusExpr(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
