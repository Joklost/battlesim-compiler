package com.company.Steps;

import com.company.Objects.DynamicObjects.DynObj;

/**
 * Created by Magnus on 01-05-2016.
 */
public abstract class Step{
    public boolean isDone = false;
    public DynObj object; //Soldier, Group, Platoon og Force nedarver fra DynObj (Dynamisk objekt)

    protected Step(DynObj object){
        this.object = object;
    }

    //Hvis dette step ikke er færdig og objektet ikke bliver kontrolleret af andre end dette step selv så retuner true
    public boolean CanStart(){
        return !isDone && (!object.IsControlled() || object.GetController().equals(this));
    }

    protected abstract void Run();


    public void RunIfCanStart(){
        if(CanStart())
            Run();
    }
}
