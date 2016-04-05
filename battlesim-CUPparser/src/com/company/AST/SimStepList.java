package com.company.AST;
import java.util.Vector;

/**
 * Created by pgug on 05-04-16.
 */
public class SimStepList extends ASTNode{

    private Vector list;

    public SimStepList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(SimStep s) { list.addElement(s); }

    public SimStep elementAt(int i) {
        return (SimStep) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
