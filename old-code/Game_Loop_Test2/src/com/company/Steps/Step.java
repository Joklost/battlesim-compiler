package com.company.Steps;

import com.company.Objects.SimulationObjects.SimObj;

/**
 * Created by Magnus on 01-05-2016.
 */
public abstract class Step{
    public boolean isDone = false;
    public SimObj object; //Soldier, Group, Platoon og Force nedarver fra SimObj (Dynamisk objekt)

    protected Step(SimObj object){
        this.object = object;
    }

    //Hvis dette step ikke er færdig og objektet ikke bliver kontrolleret af andre end dette step selv så retuner true
    public boolean canStart(){
        return !isDone && !object.IsDead() && (!object.isControlled() || object.getController().equals(this));
    }

    protected abstract void run(double deltaT);


    public void runIfCanStart(double deltaT){
        if(canStart())
            run(deltaT);
    }
}
