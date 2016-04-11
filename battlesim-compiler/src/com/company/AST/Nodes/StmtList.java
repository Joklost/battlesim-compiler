package com.company.AST.Nodes;

import java.util.Vector;

/**
 * Created by joklost on 01-04-16.
 */
public class StmtList extends ASTNode {
    private Vector list;

    public StmtList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(Stmt s) {
        list.addElement(s);
    }

    public Stmt elementAt(int i) {
        return (Stmt) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
