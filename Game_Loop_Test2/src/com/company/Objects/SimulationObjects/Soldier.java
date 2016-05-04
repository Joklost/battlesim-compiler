package com.company.Objects.SimulationObjects;

import com.company.DSTFunctions;
import com.company.FireBulletEvent;
import com.company.FireBulletListener;
import com.company.Objects.Bullet;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Soldier extends SimObj {
    private List _listeners = new ArrayList();

    public Coord Pos = new Coord(0,0);
    public final static double DefaultVelocity = 2.2; //meter per second
    public int Size = 4;            //Change this because this is a random number
    public boolean IsEnemyDetected = false;
    public int Fov = 50;
    public int Ammo = 30;
    public int FireRate = 1000; //firerate in milliseconds
    public double CL_FireRate = FireRate;
    public Soldier Enemy;
    public double Velocity = 0;
    public Vector Direction = new Vector();
    public int Magazines = 4;

    public Coord GetPos(){
        return Pos;
    }

    public void Move(Coord target){
        if(Velocity == 0){
            Velocity = DefaultVelocity;
        }
        Direction = DSTFunctions.FindUnitVector(Pos, target);
    }

    public void Move(Coord target, double velocity){
        Velocity = velocity;
        Direction = DSTFunctions.FindUnitVector(Pos, target);
    }

    public void StopMovement(){
        Velocity = 0;
    }

    public void EnemyDetected(Soldier enemy){
        Enemy = enemy;
        IsEnemyDetected = true;
    }

    public synchronized void addFireBulletListener( FireBulletListener l ) {
        _listeners.add( l );
    }

    public synchronized void removeFireBulletListener( FireBulletListener l ) {
        _listeners.remove( l );
    }

    private synchronized void _fireFireBulletEvent(Bullet bullet) {
        FireBulletEvent be = new FireBulletEvent( this, bullet );
        Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
            ( (FireBulletListener) listeners.next() ).BulletFired(be);
        }
    }

    public void TryShoot(Coord target){
        if(Ammo > 0 && CL_FireRate >= FireRate){
            Bullet bullet = new Bullet(Pos, Vector.GetVectorByPoints(Pos, target));
            _fireFireBulletEvent(bullet);
            Ammo--;
            CL_FireRate = 0;
        }
    }

    public void serviceTimers(double deltaT){
        if(CL_FireRate < FireRate){
            CL_FireRate += deltaT;
        }
    }

}
