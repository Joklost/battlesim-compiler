package com.company.AST.Nodes;

import com.company.ContextualAnalysis.HelperClasses.Array2DTypeDescriptor;

/**
 * Created by joklost on 12-04-16.
 */
public class Array2D extends TypeIdentifier {
    public TypeName typeName;
    public Expression size1;
    public Expression size2;
    public Array2DTypeDescriptor typeDesc;

    public Array2D(TypeName tn, Expression e1, Expression e2, int ln) {
        super(ln);
        this.typeName = tn;
        this.size1 = e1;
        this.size2 = e2;
    }
}
