package com.company.AST;

/**
 * Created by pgug on 05-04-16.
 */
public class SimStep extends ASTNode {

    public int stepNumber;
    public StmtList stmtList;

    public SimStep(int step, StmtList stmtl, int ln) {

        super(ln);
        this.stepNumber = step;
        this.stmtList = stmtl;
    }
}
