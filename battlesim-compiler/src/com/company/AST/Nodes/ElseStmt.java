package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ElseStmt extends ElifStmt {
    public StmtList stmtList;
    public ElseStmt(StmtList s, int ln) {
        super(ln);
        this.stmtList = s;
    }
}
