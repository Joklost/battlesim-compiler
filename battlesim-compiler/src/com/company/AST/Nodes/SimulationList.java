package com.company.AST.Nodes;
import java.util.Vector;

/**
 * Created by pgug on 05-04-16.
 */
public class SimulationList extends ASTNode {

    private Vector list;
    public SimulationList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(Simulation s) { list.addElement(s); }

    public Simulation elementAt(int i) {
        return (Simulation) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
