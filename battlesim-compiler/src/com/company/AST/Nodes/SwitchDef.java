package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class SwitchDef extends ASTNode {
    public StmtList stmtList;

    public SwitchDef(StmtList s, int ln) {
        super(ln);
        this.stmtList = s;
    }

}
