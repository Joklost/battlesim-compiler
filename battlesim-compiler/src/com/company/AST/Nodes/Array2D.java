package com.company.AST.Nodes;

/**
 * Created by joklost on 12-04-16.
 */
public class Array2D extends TypeIdentifier {
    public TypeName typeName;
    public Expression index1;
    public Expression index2;

    public Array2D(TypeName tn, Expression e1, Expression e2, int ln) {
        super(ln);
        this.typeName = tn;
        this.index1 = e1;
        this.index2 = e2;
    }
}
