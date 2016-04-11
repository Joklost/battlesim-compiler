package com.company.AST.Nodes;

/**
 * Created by joklost on 02-04-16.
 */
public class NestedIdExpr extends Expression {
    public NestedIdentifier nestedIdentifier;

    public NestedIdExpr(NestedIdentifier nid, int ln) {
        super(ln);
        this.nestedIdentifier = nid;
    }
}
