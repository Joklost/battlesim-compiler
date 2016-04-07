package com.company.AST;

/**
 * Created by joklost on 03-04-16.
 */
public class CoordExpr extends Expression {
    public Expression expression1;
    public Expression expression2;

    public CoordExpr(Expression e1, Expression e2, int ln) {
        super(ln);
        this.expression1 = e1;
        this.expression2 = e2;
    }
}
