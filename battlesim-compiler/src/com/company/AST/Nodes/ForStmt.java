package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ForStmt extends Stmt {
    public IdentifierReferencing var;
    public ForIterator forIterator;
    public Expression toExpr;
    public StmtList stmtList;

    public ForStmt(IdentifierReferencing id, ForIterator fi, Expression e, StmtList s, int ln) {
        super(ln);
        this.var = id;
        this.forIterator = fi;
        this.toExpr = e;
        this.stmtList = s;
    }
}
