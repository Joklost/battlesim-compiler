package com.BattleSim;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Vector {
    public double X = 0;
    public double Y = 0;

    public double GetLength(){
        return Math.abs(Math.sqrt( Math.pow(X, 2) + Math.pow(Y, 2)));
    }

    public void Scale(double scale){
        X *= scale;
        Y *= scale;
    }
    public static Vector GetVectorByPoints(Coord p1, Coord p2){
        Vector res = new Vector();
        res.X = p2.X - p1.X;
        res.Y = p2.Y - p1.Y;
        return res;
    }

    public Vector Dot(Vector d) {
        Vector res = new Vector();
        res.X = X * d.Y;
        res.Y = Y * d.Y;
        return res;
    }

    public Vector Normalize(){
        Vector res = this;
        double length = res.GetLength();
        res.X /= length;
        res.Y /= length;
        return res;
    }
}
