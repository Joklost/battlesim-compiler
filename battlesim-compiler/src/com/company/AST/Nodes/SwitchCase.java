package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class SwitchCase extends ASTNode {
    public Expression expression;
    public StmtList stmtList;

    public SwitchCase(Expression e, StmtList s, int ln) {
        super(ln);
        this.expression = e;
        this.stmtList = s;
    }
}
