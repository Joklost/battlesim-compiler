package com.company.Objects.StaticObjects;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Coord {
    public double X = 0;
    public double Y = 0;

    public Coord(double x, double y){
        X = x;
        Y = y;
    }

    public void NewPos(Vector vec, double velocity){
        this.X = this.X + (vec.X * velocity);
        this.Y = this.Y + (vec.Y * velocity);
    }
}
