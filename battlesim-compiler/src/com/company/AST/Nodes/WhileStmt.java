package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class WhileStmt extends Stmt {
    public Expression condition;
    public StmtList stmtList;

    public WhileStmt(Expression e, StmtList s, int ln) {
        super(ln);
        this.condition = e;
        this.stmtList = s;
    }
}
