package com.company.AST;

import java.util.Vector;

/**
 * Created by joklost on 01-04-16.
 */
public class ParamList extends ASTNode {
    private Vector list;

    public ParamList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(Param p) {
        list.addElement(p);
    }

    public Param elementAt(int i) {
        return (Param) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }

}
