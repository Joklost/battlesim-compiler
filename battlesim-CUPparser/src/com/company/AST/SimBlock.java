package com.company.AST;

/**
 * Created by Magnus on 06-04-2016.
 */
public class SimBlock extends ASTNode {

    public SimulationList simulationList;

    public SimBlock(SimulationList sl, int ln) {
        super(ln);
        this.simulationList = sl;
    }
}
