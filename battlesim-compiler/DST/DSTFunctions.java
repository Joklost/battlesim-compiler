package com.BattleSim;

import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class DSTFunctions {
    public static Vector FindUnitVector(Coord startCoord, Coord targetCoord){
        Vector res = new Vector();
        res.X = targetCoord.X - startCoord.X;
        res.Y = targetCoord.Y - startCoord.Y;
        double length = res.GetLength();
        res.X /= length;
        res.Y /= length;
        return res;
    }

    public static Coord CenterOfMass(List<Coord> coords){
        Coord res = new Coord();
        for(Coord coord : coords){
            res.X += coord.X;
            res.Y += coord.Y;
        }
        res.X /= coords.size();
        res.Y /= coords.size();

        return res;
    }
}
