package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ReturnExpr extends Stmt {
    public Expression returnVal;

    public ReturnExpr(Expression e, int ln) {
        super(ln);
        this.returnVal = e;
    }
}
