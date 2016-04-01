package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class IfStmt extends Stmt {
    public Expression expression;
    public StmtList stmtList;
    public ElseStmt elseStmt;

    public IfStmt(Expression e, StmtList s, ElseStmt es, int ln) {
        super(ln);
        this.expression = e;
        this.stmtList = s;
        this.elseStmt = es;
    }
}
