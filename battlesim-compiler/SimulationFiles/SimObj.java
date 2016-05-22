package com.BattleSim;


/**
 * Created by Magnus on 01-05-2016.
 */
public abstract class SimObj {
    protected boolean Semaphor;
    protected Step Controller; //Maybe not necessary with EmptyStep

    public int Size = 3;
    public int ReloadSpeed = 3000; //ms

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

    public abstract void setVector(Coord coord);

    public abstract Coord GetPos();

    public abstract boolean IsDead();

    public abstract int CountAliveSoldiers();

}
