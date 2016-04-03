package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifierArrayMember extends NestedIdentifier {
    public Expression expression;
    public NestedIdentifier nestedIdentifier;

    public NestedIdentifierArrayMember(Identifier id, Expression e, NestedIdentifier nid, int ln) {
        super(id, ln);
        this.expression = e;
        this.nestedIdentifier = nid;
    }
}
