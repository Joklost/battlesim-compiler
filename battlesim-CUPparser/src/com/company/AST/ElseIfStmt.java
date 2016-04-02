package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class ElseIfStmt extends ElseStmt {
    public IfStmt ifStmt;

    public ElseIfStmt(IfStmt ifs, int ln) {
        super(ln);
        this.ifStmt = ifs;
    }
}
