package com.company.AST;

import java.util.Vector;

/**
 * Created by joklost on 01-04-16.
 */
public class ArgumentList extends ASTNode {
    private Vector list;

    public ArgumentList(int ln) {
        super(ln);
    }

    public void addElement(Expression e) {
        list.addElement(e);
    }

    public Expression elementAt(int i) {
        return (Expression) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
