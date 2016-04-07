package com.company.AST;

/**
 * Created by pgug on 05-04-16.
 */
public class Interrupts extends ASTNode {
    public StmtList stmtList;

    public Interrupts(StmtList s, int ln) {
        super(ln);
        this.stmtList = s;
    }
}
