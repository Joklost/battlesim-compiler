package com.company.AST.Nodes;

/**
 * Created by joklost on 4/22/16.
 */
public class PlusPlusStmt extends Stmt {
    public IdentifierReferencing id;
    public PlusPlusStmt(IdentifierReferencing id, int ln) {
        super(ln);
        this.id = id;
    }
}
