package com.company.AST.Nodes;

/**
 * Created by joklost on 01-04-16.
 */
public class BooleanLiteral extends StdLiteral {
//    public Boolean bool;

    public BooleanLiteral(Boolean b, int ln) {
        super(ln);
        this.value = b;
    }
}
