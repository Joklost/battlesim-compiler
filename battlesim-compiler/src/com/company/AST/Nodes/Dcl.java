package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class Dcl extends Stmt {
    public DclIdList dclIdList;
    public TypeIdentifier typeIdentifier;

    public Dcl(DclIdList d, TypeIdentifier t, int ln) {
        super(ln);
        this.dclIdList = d;
        this.typeIdentifier = t;
    }

}
