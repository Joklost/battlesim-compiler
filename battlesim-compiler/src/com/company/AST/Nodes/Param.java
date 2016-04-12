package com.company.AST.Nodes;


/**
 * Created by joklost on 01-04-16.
 */
public class Param extends ASTNode{
    public Identifier identifier;
    public TypeIdentifier typeIdentifier;

    public Param(Identifier id, TypeIdentifier tid, int ln) {
        super(ln);
        this.identifier = id;
        this.typeIdentifier = tid;
    }
}
