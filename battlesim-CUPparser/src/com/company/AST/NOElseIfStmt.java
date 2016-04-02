package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class NOElseIfStmt extends ElseStmt {
    public IfStmt ifStmt;

    public NOElseIfStmt(IfStmt ifs, int ln) {
        super(ln);
        this.ifStmt = ifs;
    }
}
