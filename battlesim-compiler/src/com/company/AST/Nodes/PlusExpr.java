package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class PlusExpr extends Expression {
    public Expression leftExpr;
    public Expression rightExpr;

    public PlusExpr(Expression e1, Expression e2, int ln) {
        super(ln);
        this.leftExpr = e1;
        this.rightExpr = e2;
    }
}
