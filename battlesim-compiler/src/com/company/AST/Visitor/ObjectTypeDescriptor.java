package com.company.AST.Visitor;

import com.company.AST.SymbolTable.SymbolTable;

import static com.company.AST.Visitor.Types.noType;

public class ObjectTypeDescriptor {
    public SymbolTable fields;
    public int type = noType;
}
