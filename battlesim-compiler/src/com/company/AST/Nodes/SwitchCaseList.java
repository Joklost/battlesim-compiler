package com.company.AST.Nodes;

import java.util.Vector;

/**
 * Created by joklost on 01-04-16.
 */
public class SwitchCaseList extends ASTNode {
    private Vector list;

    public SwitchCaseList(int ln) {
        super(ln);
        this.list = new Vector();
    }

    public void addElement(SwitchCase s) {
        list.addElement(s);
    }

    public SwitchCase elementAt(int i) {
        return (SwitchCase) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
