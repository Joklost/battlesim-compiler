package com.company.AST.Nodes;

/**
 * Created by joklost on 12-04-16.
 */
public class Array1D extends TypeIdentifier {
    public TypeName typeName;
    public Expression index;

    public Array1D(TypeName tn, Expression e, int ln) {
        super(ln);
        this.typeName = tn;
        this.index = e;
    }
}
