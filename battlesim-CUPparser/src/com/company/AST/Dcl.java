package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class Dcl extends Stmt {
    public String identifier;
    public DclIdList dclIdList;
    public TypeIdentifier typeIdentifier;

    public Dcl(String id, DclIdList d, TypeIdentifier t, int ln) {
        super(ln);
        this.identifier = id;
        this.dclIdList = d;
        this.typeIdentifier = t;
    }

}
