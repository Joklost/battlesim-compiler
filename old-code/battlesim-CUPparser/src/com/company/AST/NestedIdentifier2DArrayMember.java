package com.company.AST;

import com.company.AST.NestedIdentifier;

/**
 * Created by joklost on 03-04-16.
 */
public class NestedIdentifier2DArrayMember extends NestedIdentifier {
    public Expression expression1;
    public Expression expression2;
    public NestedIdentifier nestedIdentifier;

    public NestedIdentifier2DArrayMember(Identifier id, Expression e1, Expression e2, NestedIdentifier ni, int ln) {
        super(id, ln);
        this.expression1 = e1;
        this.expression2 = e2;
        this.nestedIdentifier = ni;
    }
}
