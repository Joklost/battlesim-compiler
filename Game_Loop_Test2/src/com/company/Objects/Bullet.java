package com.company.Objects;

import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

/**
 * Created by Magnus on 03-05-2016.
 */
public class Bullet {
    public Coord firePos;
    public Vector vector;
    public int owner;

    public Bullet(Coord firePos, Vector vec, int owner){
        this.firePos = firePos;
        vec.x *= 10000;
        vec.y *= 10000;
        this.vector = vec;
        this.owner = owner;
    }
}
