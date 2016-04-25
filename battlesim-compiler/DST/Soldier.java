package com.company.DST;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Soldier {
    public final double DefaultVelocity = 3; //Change this because this is a random number
    private int Size = 4;            //Change this because this is a random number

    public double Velocity = 0;
    public Vector Direction;
    public Coord SCoord;
    public int Magazines = 4;

    public void Move(Coord target){
        if(Velocity == 0){
            Velocity = DefaultVelocity;
        }
        Direction = DSTFunctions.FindUnitVector(SCoord, target);
    }

    public void Move(Coord target, double velocity){
        Velocity = velocity;
        Direction = DSTFunctions.FindUnitVector(SCoord, target);
    }

    public void StopMovement(){
        Velocity = 0;
    }

}
