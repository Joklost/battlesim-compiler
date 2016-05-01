package com.company.Objects.StaticObjects;

import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Barrier {
    public List<Coord> Vertices;

    public Barrier(){

    }

    public void AddVertex(Coord vertex){
        this.Vertices.add(vertex);
    }
}
