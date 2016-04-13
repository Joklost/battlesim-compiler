package com.company.AST.Nodes;

/**
 * Created by pgug on 05-04-16.
 */
public class Simulation extends ASTNode {

    public Identifier identifier;
    public IdentifierReferencing objectIdentifier;
    public SimStepList simStepList;
    public Interrupts interrupts;


    public Simulation(Identifier id, IdentifierReferencing oi, SimStepList ssl, Interrupts inter, int ln) {
        super(ln);
        this.identifier = id;
        this.objectIdentifier = oi;
        this.simStepList = ssl;
        this.interrupts = inter;
    }
}
