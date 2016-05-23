package com.BattleSim;

/**
 * Created by Magnus on 01-05-2016.
 */
public abstract class Step{
    public boolean isDone = false;
    public SimObj object; //Soldier, Group, Platoon og Force nedarver fra SimObj (Dynamisk objekt)
    public boolean isInterrupt = false;

    protected Step(SimObj object){
        this.object = object;
    }

    //Hvis dette step ikke er færdig og objektet ikke bliver kontrolleret af andre end dette step selv så returner true
    public boolean CanStart(){
        return !isDone && !object.IsDead() && (!object.IsControlled() || object.GetController().equals(this));
    }

    protected abstract void Run(double deltaT);


    public void RunIfCanStart(double deltaT){
        if(CanStart())
            Run(deltaT);
    }

    public boolean canStartInterrupt(){
        return !isDone && !object.IsDead() && (!object.IsControlled() || object.GetController().equals(this) || !object.GetController().isInterrupt);
    }

    public void interrupt(double delta){
        isInterrupt = true;
        if(canStartInterrupt())
            Run(delta);
    }
}
