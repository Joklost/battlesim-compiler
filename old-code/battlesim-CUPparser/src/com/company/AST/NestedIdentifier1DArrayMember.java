package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifier1DArrayMember extends NestedIdentifier {
    public Expression expression;
    public NestedIdentifier nestedIdentifier;

    public NestedIdentifier1DArrayMember(Identifier id, Expression e, NestedIdentifier nid, int ln) {
        super(id, ln);
        this.expression = e;
        this.nestedIdentifier = nid;
    }
}
