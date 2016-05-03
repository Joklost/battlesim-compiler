package com.company.Objects;

import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

/**
 * Created by Magnus on 03-05-2016.
 */
public class Bullet {
    public Coord FirePos;
    public Vector Vec;

    public Bullet(Coord firePos, Vector vec){
        FirePos = firePos;
        Vec = vec;
    }
}
