package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class FunctionCallExpr extends Expression {
    public FunctionCall functionCall;

    public FunctionCallExpr(FunctionCall fc, int ln) {
        super(ln);
        this.functionCall = fc;
    }
}
