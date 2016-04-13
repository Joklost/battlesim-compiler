package com.company.AST.Nodes;

/**
 * Created by joklost on 13-04-16.
 */
public class Array2DReferencing extends IdentifierReferencing {
    public IdentifierReferencing arrayName;
    public Expression firstIndexExpr;
    public Expression secondIndexExpr;

    public Array2DReferencing(IdentifierReferencing id, Expression e1, Expression e2, int ln) {
        super(ln);
        this.arrayName = id;
        this.firstIndexExpr = e1;
        this.secondIndexExpr = e2;
    }
}
