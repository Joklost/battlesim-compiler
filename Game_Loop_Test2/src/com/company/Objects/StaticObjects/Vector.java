package com.company.Objects.StaticObjects;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Vector {
    public float X = 0;
    public float Y = 0;

    public float GetLength(){
        return (float) Math.abs(Math.sqrt( Math.pow(X, 2) + Math.pow(Y, 2)));
    }

    public static Vector GetVectorByPoints(Coord p1, Coord p2){
        Vector res = new Vector();
        res.X = p2.X - p1.X;
        res.Y = p2.Y - p1.Y;
        return res;
    }
}
