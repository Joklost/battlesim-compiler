package com.company.AST;

/**
 * Created by joklost on 02-04-16.
 */
public class NestedIdentifierArray extends NestedIdentifier {
    public Expression expression;

    public NestedIdentifierArray(Identifier id, Expression e, int ln) {
        super(id, ln);
        this.expression = e;
    }
}
