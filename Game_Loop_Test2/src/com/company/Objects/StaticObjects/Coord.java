package com.company.Objects.StaticObjects;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Coord {
    public double X = 0;
    public double Y = 0;

    public Coord(){
    }

    public Coord(double x, double y){
        X = x;
        Y = y;
    }

    public void NewPos(Vector vec, double velocity, double deltaT){
        this.X = this.X + deltaT * (vec.X * velocity);
        this.Y = this.Y + deltaT * (vec.Y * velocity);
    }
}
