package com.company.Steps;

import com.company.Objects.DynamicObjects.DynObj;
import com.company.Objects.StaticObjects.Coord;

/**
 * Created by Magnus on 01-05-2016.
 */
public class MoveStep extends Step{
    public Coord coord = new Coord(0,0);

    public MoveStep(DynObj object, Coord coord){
        super(object);
        this.coord = coord;
    }

    public void Run(){
        object.Take(this);
        object.Move(coord);
        if(object.GetPos() == coord){
            object.StopMovement();
            object.Release();
            isDone = true;
        }
    }
}
