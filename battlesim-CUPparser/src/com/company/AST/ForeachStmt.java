package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class ForeachStmt extends Stmt {
    public TypeIdentifier typeIdentifier;
    public Identifier identifier;
    public NestedIdentifier nestedIdentifier;
    public StmtList stmtList;

    public ForeachStmt(TypeIdentifier t, Identifier id, NestedIdentifier nid, StmtList s, int ln) {
        super(ln);
        this.typeIdentifier = t;
        this.identifier = id;
        this.nestedIdentifier = nid;
        this.stmtList = s;
    }
}
