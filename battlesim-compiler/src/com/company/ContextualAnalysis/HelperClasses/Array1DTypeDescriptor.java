package com.company.ContextualAnalysis.HelperClasses;

import com.company.AST.Nodes.Expression;

/**
 * Created by joklost on 15-04-16.
 */
public class Array1DTypeDescriptor extends TypeDescriptor {
    public int elementType = TypeConsts.noType;
    public Expression arraySize;
}
