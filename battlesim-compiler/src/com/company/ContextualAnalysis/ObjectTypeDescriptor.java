package com.company.ContextualAnalysis;

import com.company.AST.SymbolTable.SymbolTable;

public class ObjectTypeDescriptor extends TypeDescriptor {
    public SymbolTable fields;
    public int type = TypeConsts.objectType;
}
