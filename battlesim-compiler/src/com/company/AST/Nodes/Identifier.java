package com.company.AST.Nodes;

/**
 * Created by joklost on 12-04-16.
 */
public class Identifier extends ASTNode {
    public String name;
    public ASTNode def;
    public int type;

    public Identifier(String id, int ln) {
        super(ln);
        this.name = id;
    }
}
