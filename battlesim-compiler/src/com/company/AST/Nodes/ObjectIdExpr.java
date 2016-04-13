package com.company.AST.Nodes;

/**
 * Created by joklost on 02-04-16.
 */
public class ObjectIdExpr extends Expression {
    public IdentifierReferencing objectName;

    public ObjectIdExpr(IdentifierReferencing nid, int ln) {
        super(ln);
        this.objectName = nid;
    }
}
