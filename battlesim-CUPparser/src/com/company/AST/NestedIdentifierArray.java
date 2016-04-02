package com.company.AST;

/**
 * Created by joklost on 02-04-16.
 */
public class NestedIdentifierArray extends NestedIdentifier {
    public String identifier;
    public Expression expression;

    public NestedIdentifierArray(String id, Expression e, int ln) {
        super(ln);
        this.identifier = id;
        this.expression = e;
    }
}
