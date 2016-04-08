package com.company.AST;

/**
 * Created by pgug on 05-04-16.
 */
public class Simulation extends ASTNode {

    public Identifier identifier;
    public NestedIdentifier nestedIdentifier;
    public SimStepList simStepList;
    public Interrupts interrupts;


    public Simulation(Identifier id, NestedIdentifier ni, SimStepList ssl, Interrupts inter, int ln) {
        super(ln);
        this.identifier = id;
        this.nestedIdentifier = ni;
        this.simStepList = ssl;
        this.interrupts = inter;
    }
}
