package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class ForeachStmt extends Stmt {
    public TypeIdentifier typeName;
    public Identifier localName;
    public IdentifierReferencing objectName;
    public StmtList stmtList;

    public ForeachStmt(TypeIdentifier t, Identifier id, IdentifierReferencing nid, StmtList s, int ln) {
        super(ln);
        this.typeName = t;
        this.localName = id;
        this.objectName = nid;
        this.stmtList = s;
    }
}
