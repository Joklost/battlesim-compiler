package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifier extends ASTNode {
    public String identifier;

    public NestedIdentifier(String id, int ln) {
        super(ln);
        this.identifier = id;
    }
}
