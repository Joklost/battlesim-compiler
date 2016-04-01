package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifierMember extends NestedIdentifier {
    public String identifier;
    public NestedIdentifier nestedIdentifier;

    public NestedIdentifierMember(String id, NestedIdentifier nid, int ln) {
        super(ln);
        this.identifier = id;
        this.nestedIdentifier = nid;
    }
}
