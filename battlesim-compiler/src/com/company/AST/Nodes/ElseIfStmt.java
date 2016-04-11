package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ElseIfStmt extends ElifStmt {
    public Expression expression;
    public StmtList stmtList;
    public ElifStmt elifStmt;

    public ElseIfStmt(Expression e, StmtList s, ElifStmt es, int ln) {
        super(ln);
        this.expression = e;
        this.stmtList = s;
        this.elifStmt = es;
    }
}
