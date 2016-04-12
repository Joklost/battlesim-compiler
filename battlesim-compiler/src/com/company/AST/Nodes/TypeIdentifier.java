package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public abstract class TypeIdentifier extends ASTNode {
    public int type;

    public TypeIdentifier(int ln) {
        super(ln);
    }
}
