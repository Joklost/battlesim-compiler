package com.company.AST.Nodes;

/**
 * Created by joklost on 13-04-16.
 */
public class Array1DReferencing extends IdentifierReferencing {
    public IdentifierReferencing arrayName;
    public Expression indexExpr;

    public Array1DReferencing(IdentifierReferencing id, Expression e, int ln) {
        super(ln);
        this.arrayName = id;
        this.indexExpr = e;
    }

}
