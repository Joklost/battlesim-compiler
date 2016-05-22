package com.company.Objects.SimulationObjects;

import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;
import com.company.Steps.Step;

/**
 * Created by Magnus on 01-05-2016.
 */
public abstract class SimObj {
    protected boolean semaphor;
    protected Step controller; //Maybe not necessary with EmptyStep

    public int size = 3;
    public int reloadSpeed = 3000; //ms

    public boolean isControlled(){
        return semaphor;
    }

    public Step getController(){
        return controller;
    }
    public void take(Step controller){
        semaphor = true;
        this.controller = controller;
    }

    public void release(){
        semaphor = false;
    }

    public abstract void stopMovement();

    public abstract void setVector(Coord coord);

    public abstract Coord getPos();

    public abstract boolean IsDead();

    public abstract int CountAliveSoldiers();

}
