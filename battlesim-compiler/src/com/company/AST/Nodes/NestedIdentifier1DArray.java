package com.company.AST.Nodes;

/**
 * Created by joklost on 02-04-16.
 */
public class NestedIdentifier1DArray extends NestedIdentifier {
    public Expression expression;

    public NestedIdentifier1DArray(Identifier id, Expression e, int ln) {
        super(id, ln);
        this.expression = e;
    }
}
