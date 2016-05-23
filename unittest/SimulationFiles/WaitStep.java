package com.BattleSim;

/**
 * Created by Magnus on 03-05-2016.
 */
public class WaitStep extends Step{
    private double time;
    private double ticker = 0;

    public WaitStep(SimObj object, double time){
        super(object);
        this.time = time * 1000;
    }

    public void Run(double deltaT){
        object.Take(this);
        object.StopMovement();
        if(ticker >= time){
            object.Release();
            isDone = true;
        }
        ticker += deltaT;
    }
}
