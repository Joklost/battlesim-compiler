package com.company.Objects.StaticObjects;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Vector {
    public double x = 0;
    public double y = 0;

    public double getLength(){
        return Math.abs(Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public void scale(double scale){
        x *= scale;
        y *= scale;
    }
    public static Vector getVectorByPoints(Coord p1, Coord p2){
        Vector res = new Vector();
        res.x = p2.x - p1.x;
        res.y = p2.y - p1.y;
        return res;
    }

    public Vector dot(Vector d) {
        Vector res = new Vector();
        res.x = x * d.y;
        res.y = y * d.y;
        return res;
    }

    public Vector normalize(){
        Vector res = this;
        double length = res.getLength();
        res.x /= length;
        res.y /= length;
        return res;
    }
}
