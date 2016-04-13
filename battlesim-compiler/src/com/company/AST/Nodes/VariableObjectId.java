package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class VariableObjectId extends Variable {
    public IdentifierReferencing objectName;

    public VariableObjectId(IdentifierReferencing nid, int ln) {
        super(ln);
        this.objectName = nid;
    }
}
