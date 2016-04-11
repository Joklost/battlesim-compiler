package com.company.AST.Nodes;

/**
 * Created by joklost on 02-04-16.
 */
public class StdLiteralExpr extends Expression {
    public StdLiteral stdLiteral;

    public StdLiteralExpr(StdLiteral std, int ln) {
        super(ln);
        this.stdLiteral = std;
    }
}
