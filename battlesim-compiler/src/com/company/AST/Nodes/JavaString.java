package com.company.AST.Nodes;

/**
 * Created by joklost on 5/10/16.
 */
public class JavaString extends ASTNode {
    public String javaCode;

    public JavaString(String java, int ln) {
        super(ln);
        this.javaCode = java;
    }
}
