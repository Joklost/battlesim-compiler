package com.company.AST.Nodes;

import java.util.Vector;

/**
 * Created by joklost on 4/26/16.
 */
public class DeclarationList extends ASTNode {
    private Vector list;

    public DeclarationList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(Dcl f) {
        list.addElement(f);
    }

    public Dcl elementAt(int i) {
        return (Dcl) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }


}
