package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ReturnExpr extends Stmt {
    public Expression expression;

    public ReturnExpr(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
