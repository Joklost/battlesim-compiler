package com.company;

import com.company.Objects.Bullet;

import java.util.EventObject;

/**
 * Created by Magnus on 04-05-2016.
 */
public class FireBulletEvent extends EventObject {
    private Bullet _bullet;
    public FireBulletEvent(Object o, Bullet bullet) {
        super(o);
        _bullet = bullet;
    }

    public Bullet GetBullet(){
        return _bullet;
    }
}
