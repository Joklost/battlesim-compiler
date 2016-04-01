package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class TypeArray extends TypeIdentifier {
    public TypeIdentifier typeIdentifier;
    public Expression expression;

    public TypeArray(TypeIdentifier tid, Expression e, int ln) {
        super(ln);
        this.typeIdentifier = tid;
        this.expression = e;
    }
}
