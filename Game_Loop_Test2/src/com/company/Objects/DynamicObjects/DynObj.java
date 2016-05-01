package com.company.Objects.DynamicObjects;

import com.company.Objects.StaticObjects.Coord;
import com.company.Steps.EmptyStep;
import com.company.Steps.Step;

/**
 * Created by Magnus on 01-05-2016.
 */
public abstract class DynObj {
    private boolean Semaphor;

    private Step Controller; //Maybe not necessary with EmptyStep

    public boolean IsControlled(){
        return Semaphor;
    }

    public Step GetController(){
        return Controller;
    }
    public void Take(Step controller){
        if(!IsControlled()){
            Semaphor = true;
            Controller = controller;
        }
    }

    public void Release(){
        Semaphor = false;
    }

    public abstract void StopMovement();

    public abstract void Move(Coord coord);

    public abstract Coord GetPos();
}
