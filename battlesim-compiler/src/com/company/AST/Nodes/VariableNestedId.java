package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class VariableNestedId extends Variable {
    public NestedIdentifier nestedIdentifier;

    public VariableNestedId(NestedIdentifier nid, int ln) {
        super(ln);
        this.nestedIdentifier = nid;
    }
}
