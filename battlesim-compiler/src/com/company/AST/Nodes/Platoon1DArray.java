package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class Platoon1DArray extends TypeIdentifier {
    public Expression expression;

    public Platoon1DArray(Expression e, int ln) {
        super(ln);
        this.expression = e;
    }
}
