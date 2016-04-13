package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class FunctionCall extends ASTNode {
    public IdentifierReferencing objectName;
    public ArgumentList argumentList;

    public FunctionCall(IdentifierReferencing nid, ArgumentList al, int ln) {
        super(ln);
        this.objectName = nid;
        this.argumentList = al;
    }
}
