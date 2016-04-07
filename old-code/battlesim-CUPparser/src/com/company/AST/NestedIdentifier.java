package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class NestedIdentifier extends ASTNode {
    public Identifier identifier;

    public NestedIdentifier(Identifier id, int ln) {
        super(ln);
        this.identifier = id;
    }
}
