package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifierMember extends NestedIdentifier {
    public NestedIdentifier nestedIdentifier;

    public NestedIdentifierMember(Identifier id, NestedIdentifier nid, int ln) {
        super(id, ln);
        this.nestedIdentifier = nid;
    }
}
