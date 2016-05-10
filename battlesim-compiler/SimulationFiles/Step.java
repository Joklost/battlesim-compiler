package com.BattleSim;

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
    public boolean CanStart(){
        return !isDone && !object.IsDead() && (!object.IsControlled() || object.GetController().equals(this));
    }

    protected abstract void Run(double deltaT);


    public void RunIfCanStart(double deltaT){
        if(CanStart())
            Run(deltaT);
    }
}
