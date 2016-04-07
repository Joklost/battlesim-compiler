package com.company.AST;

/**
 * Created by joklost on 03-04-16.
 */
public class NestedIdentifier2DArray extends NestedIdentifier {
    public Expression expression1;
    public Expression expression2;

    public NestedIdentifier2DArray(Identifier id, Expression e1, Expression e2, int ln) {
        super(id, ln);
        this.expression1 = e1;
        this.expression2 = e2;
    }
}
