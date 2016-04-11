package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class FunctionCall extends ASTNode {
    public NestedIdentifier nestedIdentifier;
    public ArgumentList argumentList;

    public FunctionCall(NestedIdentifier nid, ArgumentList al, int ln) {
        super(ln);
        this.nestedIdentifier = nid;
        this.argumentList = al;
    }
}
