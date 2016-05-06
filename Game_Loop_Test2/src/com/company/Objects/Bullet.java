package com.company.Objects;

import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

/**
 * Created by Magnus on 03-05-2016.
 */
public class Bullet {
    public Coord FirePos;
    public Vector Vec;
    public int Owner;

    public Bullet(Coord firePos, Vector vec, int owner){
        FirePos = firePos;
        vec.Y *= 10000;
        vec.X *= 10000;
        Vec = vec;
        Owner = owner;
    }
}
