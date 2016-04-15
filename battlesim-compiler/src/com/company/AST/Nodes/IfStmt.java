package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class IfStmt extends Stmt {
    public Expression condition;
    public StmtList stmtList;
    public ElifStmt elseStmt;

    public IfStmt(Expression e, StmtList s, ElifStmt es, int ln) {
        super(ln);
        this.condition = e;
        this.stmtList = s;
        this.elseStmt = es;
    }
}
