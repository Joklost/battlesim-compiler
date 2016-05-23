package com.company.Instructions;

import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

/**
 * Created by Magnus on 01-05-2016.
 */
public class MoveInstruction extends Instruction {
    public Coord coord = new Coord(0,0);

    public MoveInstruction(SimObj object, Coord coord){
        super(object);
        this.coord = coord;
    }

    public void run(double deltaT){
        object.take(this);
        object.setVector(coord);
        Vector v = Vector.getVectorByPoints(object.getPos(), coord);
        if(v.getLength() < object.size){
            object.stopMovement();
            object.release();
            isDone = true;
        }
    }
}
