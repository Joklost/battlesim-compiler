package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifierArray extends NestedIdentifier {
    public String identifier;
    public Expression expression;
    public NestedIdentifier nestedIdentifier;

    public NestedIdentifierArray(String id, Expression e, NestedIdentifier nid, int ln) {
        super(ln);
        this.identifier = id;
        this.expression = e;
        this.nestedIdentifier = nid;
    }
}
