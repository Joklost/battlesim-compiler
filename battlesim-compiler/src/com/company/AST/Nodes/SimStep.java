package com.company.AST.Nodes;

/**
 * Created by pgug on 05-04-16.
 */
public class SimStep extends ASTNode {

    public Integer stepNumber;
    public StmtList stmtList;

    public SimStep(Integer step, StmtList stmtl, int ln) {

        super(ln);
        this.stepNumber = step;
        this.stmtList = stmtl;
    }
}
