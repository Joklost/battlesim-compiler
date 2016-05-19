package com.BattleSim;

/**
 * Created by Magnus on 01-05-2016.
 */
public class MoveStep extends Step{
    public Coord coord = new Coord();

    public MoveStep(SimObj object, Coord coord){
        super(object);
        this.coord = coord;
    }

    public void Run(double deltaT){
        object.Take(this);
        object.setVector(coord);
        Vector v = Vector.GetVectorByPoints(object.GetPos(), coord);
        if(v.GetLength() < object.Size){
            object.StopMovement();
            object.Release();
            isDone = true;
        }
    }
}
