package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifierMember extends NestedIdentifier {
    public NestedIdentifier nestedIdentifier;

    public NestedIdentifierMember(String id, NestedIdentifier nid, int ln) {
        super(id, ln);
        this.nestedIdentifier = nid;
    }
}
