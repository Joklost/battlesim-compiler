package com.company.AST.Nodes;

import java.util.Vector;

/**
 * Created by joklost on 5/10/16.
 */
public class JavaStringList extends ASTNode {
    private Vector list;

    public JavaStringList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(JavaString f) {
        list.addElement(f);
    }

    public JavaString elementAt(int i) {
        return (JavaString) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }


}
