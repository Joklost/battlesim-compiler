package com.company.ContextualAnalysis;

import com.company.AST.Nodes.Expression;

import static com.company.ContextualAnalysis.TypeConsts.noType;

/**
 * Created by joklost on 15-04-16.
 */
public class Array1DTypeDescriptor extends TypeDescriptor {
    public int elementType = noType;
    public Expression arraySize;
}
