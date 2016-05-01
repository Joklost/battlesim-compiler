package com.company.Objects.DynamicObjects;

import com.company.DSTFunctions;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Soldier extends DynObj {
    public final static double DefaultVelocity = 3; //Change this because this is a random number
    private int Size = 4;            //Change this because this is a random number

    public double Velocity = 0;
    public Vector Direction = new Vector();
    public int Magazines = 4;
    public Coord Pos = new Coord(0,0);

    public Coord GetPos(){
        return Pos;
    }

    public void Move(Coord target){
        if(Velocity == 0){
            Velocity = DefaultVelocity;
        }
        Direction = DSTFunctions.FindUnitVector(Pos, target);
    }

    public void Move(Coord target, double velocity){
        Velocity = velocity;
        Direction = DSTFunctions.FindUnitVector(Pos, target);
    }

    public void StopMovement(){
        Velocity = 0;
    }

}
