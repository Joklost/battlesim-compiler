package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class SwitchCase extends ASTNode {
    public StdLiteral label;
    public StmtList stmtList;

    public SwitchCase(StdLiteral l, StmtList s, int ln) {
        super(ln);
        this.label = l;
        this.stmtList = s;
    }
}
