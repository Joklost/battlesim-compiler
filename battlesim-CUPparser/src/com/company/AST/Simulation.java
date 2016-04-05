package com.company.AST;

/**
 * Created by pgug on 05-04-16.
 */
public class Simulation extends ASTNode {

    public Identifier identifier;
    public SimStepList simStepList;
    public Interrupts interrupts;


    public Simulation(Identifier i, SimStepList ssl, Interrupts inter, int ln) {
        super(ln);
        this.identifier = i;
        this.simStepList = ssl;
        this.interrupts = inter;
    }
}
