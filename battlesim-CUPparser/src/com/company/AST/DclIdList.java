package com.company.AST;

import java.util.Vector;

/**
 * Created by joklost on 01-04-16.
 */
public class DclIdList extends ASTNode {
    private Vector list;

    public DclIdList(int ln) {
        super(ln);
    }

    public void addElement(String id) {
        list.addElement(id);
    }

    public String elementAt(int i) {
        return (String) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
