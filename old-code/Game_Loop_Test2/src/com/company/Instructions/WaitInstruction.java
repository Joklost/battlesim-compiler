package com.company.Instructions;

import com.company.Objects.SimulationObjects.SimObj;

/**
 * Created by Magnus on 03-05-2016.
 */
public class WaitInstruction extends Instruction {
    private double time;
    private double ticker = 0;

    public WaitInstruction(SimObj object, double time){
        super(object);
        this.time = time * 1000;
    }

    public void run(double deltaT){
        object.take(this);
        object.stopMovement();
        if(ticker >= time){
            object.release();
            isDone = true;
        }
        ticker += deltaT;
    }
}
