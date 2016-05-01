package com.company.Objects.StaticObjects;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Vector {
    public double X = 0;
    public double Y = 0;

    public double GetLength(){
        return Math.abs(Math.sqrt( Math.pow(X, X) + Math.pow(Y, Y)));
    }
}
