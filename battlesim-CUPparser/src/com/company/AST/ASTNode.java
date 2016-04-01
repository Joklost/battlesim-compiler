package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public abstract class ASTNode {
    protected int lineNumber;
    public ASTNode(int ln) {
        this.lineNumber = ln;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
