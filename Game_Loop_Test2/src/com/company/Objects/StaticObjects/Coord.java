package com.company.Objects.StaticObjects;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Coord {
    public float X = 0;
    public float Y = 0;

    public Coord(float x, float y){
        X = x;
        Y = y;
    }

    public void NewPos(Vector vec, float velocity){
        this.X = this.X + (vec.X * velocity);
        this.Y = this.Y + (vec.Y * velocity);
    }
}
