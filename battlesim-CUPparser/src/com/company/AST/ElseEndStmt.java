package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class ElseEndStmt extends ElseStmt {
    public StmtList stmtList;

    public ElseEndStmt(StmtList s, int ln) {
        super(ln);
        this.stmtList = s;
    }
}
