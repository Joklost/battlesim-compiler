package com.company.Objects.StaticObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Barrier {
    public List<Coord> vertices = new ArrayList<Coord>();


    public void addVertex(Coord vertex){
        this.vertices.add(vertex);
    }
}
