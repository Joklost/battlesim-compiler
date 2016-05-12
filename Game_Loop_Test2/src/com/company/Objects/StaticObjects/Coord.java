package com.company.Objects.StaticObjects;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Coord {
    public double x = 0;
    public double y = 0;

    public Coord(){
    }

    public Coord(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void newPos(Vector vec, double velocity, double deltaT){
        this.x = this.x + deltaT * (vec.x * velocity);
        this.y = this.y + deltaT * (vec.y * velocity);
    }
}
