package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class FunctionCallStmt extends Stmt {
    public FunctionCall functionCall;

    public FunctionCallStmt(FunctionCall fc, int ln) {
        super(ln);
        this.functionCall = fc;
    }
}
