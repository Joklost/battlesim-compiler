package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class LessThanEqualsExpr extends Expression {
    public Expression leftExpr;
    public Expression rightExpr;

    public LessThanEqualsExpr(Expression e1, Expression e2, int ln) {
        super(ln);
        this.leftExpr = e1;
        this.rightExpr = e2;
    }
}
