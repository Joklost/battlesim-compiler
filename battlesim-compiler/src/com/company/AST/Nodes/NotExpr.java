package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class NotExpr extends Expression {
    public Expression expression;

    public NotExpr(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
