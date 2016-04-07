package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class Program extends ASTNode {
    public StmtList stmtList;

    public Program(StmtList s, int ln) {
        super(ln);
        this.stmtList = s;
    }
}
