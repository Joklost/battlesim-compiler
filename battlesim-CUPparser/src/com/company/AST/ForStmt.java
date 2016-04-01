package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class ForStmt extends Stmt {
    public NestedIdentifier nestedIdentifier;
    public ForIterator forIterator;
    public Expression expression;
    public StmtList stmtList;

    public ForStmt(NestedIdentifier nid, ForIterator fi, Expression e, StmtList s, int ln) {
        super(ln);
        this.nestedIdentifier = nid;
        this.forIterator = fi;
        this.expression = e;
        this.stmtList = s;
    }
}
