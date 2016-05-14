package com.company.ContextualAnalysis.HelperClasses;

import com.company.AST.SymbolTable.SymbolTable;

public class ObjectTypeDescriptor extends TypeDescriptor {
    public SymbolTable fields;
    public int type = TypeConsts.objectType;
}
