package com.company.AST.Nodes;

/**
 * Created by joklost on 13-04-16.
 */
public class ObjectReferencing extends IdentifierReferencing {
    public IdentifierReferencing objectName;
    public IdentifierReferencing fieldName;

    public ObjectReferencing(IdentifierReferencing on, IdentifierReferencing fn, int ln) {
        super(ln);
        this.objectName = on;
        this.fieldName = fn;
    }
}
