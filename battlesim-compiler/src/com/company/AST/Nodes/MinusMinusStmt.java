package com.company.AST.Nodes;

/**
 * Created by joklost on 4/22/16.
 */
public class MinusMinusStmt extends UnaryStmt {
    public IdentifierReferencing id;
    public MinusMinusStmt(IdentifierReferencing id, int ln) {
        super(ln);
        this.id = id;
    }
}
