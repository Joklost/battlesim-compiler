package com.company.AST;

/**
 * Created by pgug on 05-04-16.
 */
public class Simulation extends ASTNode {

    public Identifier identifier1;
    public Identifier identifier2;
    public SimStepList simStepList;
    public Interrupts interrupts;


    public Simulation(Identifier i1, Identifier i2, SimStepList ssl, Interrupts inter, int ln) {
        super(ln);
        this.identifier1 = i1;
        this.identifier2 = i2;
        this.simStepList = ssl;
        this.interrupts = inter;
    }
}
