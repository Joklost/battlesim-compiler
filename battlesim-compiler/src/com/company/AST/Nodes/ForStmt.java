package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ForStmt extends Stmt {
    public Expression expression1;
    public ForIterator forIterator;
    public Expression expression2;
    public StmtList stmtList;

    public ForStmt(Expression e1, ForIterator fi, Expression e2, StmtList s, int ln) {
        super(ln);
        this.expression1 = e1;
        this.forIterator = fi;
        this.expression2 = e2;
        this.stmtList = s;
    }
}
