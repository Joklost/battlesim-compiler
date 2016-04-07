package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class WhileStmt extends Stmt {
    public Expression expression;
    public StmtList stmtList;

    public WhileStmt(Expression e, StmtList s, int ln) {
        super(ln);
        this.expression = e;
        this.stmtList = s;
    }
}
