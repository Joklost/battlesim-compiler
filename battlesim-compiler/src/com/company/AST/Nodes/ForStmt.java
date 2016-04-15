package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ForStmt extends Stmt {
    public Expression initialExpr;
    public ForIterator forIterator;
    public Expression toExpr;
    public StmtList stmtList;

    public ForStmt(Expression e1, ForIterator fi, Expression e2, StmtList s, int ln) {
        super(ln);
        this.initialExpr = e1;
        this.forIterator = fi;
        this.toExpr = e2;
        this.stmtList = s;
    }
}
