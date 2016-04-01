package com.company.AST;

import java.util.Vector;

/**
 * Created by joklost on 01-04-16.
 */
public class FunctionDclList extends ASTNode {
    private Vector list;

    public FunctionDclList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(FunctionDcl f) {
        list.addElement(f);
    }

    public FunctionDcl elementAt(int i) {
        return (FunctionDcl) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
