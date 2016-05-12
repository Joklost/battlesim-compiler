package com.company;

import com.company.Objects.Bullet;

import java.util.EventObject;

/**
 * Created by Magnus on 04-05-2016.
 */
public class FireBulletEvent extends EventObject {
    private Bullet bullet;
    public FireBulletEvent(Object o, Bullet bullet) {
        super(o);
        this.bullet = bullet;
    }

    public Bullet getBullet(){
        return bullet;
    }
}
