package com.company.AST.Nodes;

/**
 * Created by joklost on 4/21/16.
 */
public class JavaStringStmt extends Stmt {
    public String javaCode;
    public JavaStringStmt(String javaCode, int ln) {
        super(ln);
        this.javaCode = javaCode;
    }
}
