package com.company;

import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class DSTFunctions {
    public static Vector FindUnitVector(Coord startCoord, Coord targetCoord){
        Vector res = new Vector();
        res.X = targetCoord.X - startCoord.X;
        res.Y = targetCoord.Y - startCoord.Y;
        float length = res.GetLength();
        res.X /= length;
        res.Y /= length;
        return res;
    }

    public static Coord CenterOfMass(List<Coord> coords){
        Coord res = new Coord(0,0);
        for(Coord coord : coords){
            res.X += coord.X;
            res.Y += coord.Y;
        }
        res.X /= coords.size();
        res.Y /= coords.size();

        return res;
    }
}
