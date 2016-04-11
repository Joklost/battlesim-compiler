package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class DclBlock extends ASTNode {
    public StmtList stmtLists;

    public DclBlock(StmtList s, int ln) {
        super(ln);
        this.stmtLists = s;
    }


}
