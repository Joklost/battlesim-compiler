package com.company;

import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class DSTFunctions {
    public static Vector findUnitVector(Coord startCoord, Coord targetCoord){
        Vector res = new Vector();
        res.x = targetCoord.x - startCoord.x;
        res.y = targetCoord.y - startCoord.y;
        double length = res.getLength();
        res.x /= length;
        res.y /= length;
        return res;
    }

    public static Coord centerOfMass(List<Coord> coords){
        Coord res = new Coord(0,0);
        for(Coord coord : coords){
            res.x += coord.x;
            res.y += coord.y;
        }
        res.x /= coords.size();
        res.y /= coords.size();

        return res;
    }
}
