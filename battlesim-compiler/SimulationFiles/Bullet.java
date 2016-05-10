package com.BattleSim;

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
