package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class Assignment extends Stmt {
    public NestedIdentifier targetName;
    public AssignOp assignOp;
    public Expression expression;

    public Assignment(NestedIdentifier n, AssignOp a, Expression e, int ln) {
        super(ln);
        this.targetName = n;
        this.assignOp = a;
        this.expression = e;
    }
}
