package com.company.Objects.SimulationObjects;

import com.company.DSTFunctions;
import com.company.FireBulletEvent;
import com.company.FireBulletListener;
import com.company.Objects.Bullet;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

import java.util.*;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Soldier extends SimObj {
    //Typer med bruger adgang til
    public Coord Pos = new Coord(0,0);
    public double Velocity = 0;
    private boolean isDead = false;

    private List _listeners = new ArrayList();
    //private double accuracy = 0.0024999999999971; //accuracy er beregnet udfra at en hjemmeværnsmand skal kunne ramme en torso(0.5m bred) fra 200m afstand
    private double accuracy = 0.2;

    public int Side = 0;
    public final static double DefaultVelocity = 2.2; //meter per second
    public double Size = 0.25;            //Change this because this is a random number
    public boolean IsEnemyDetected = false;
    public int Fov = 50;
    public int Ammo = 30;
    public int FireRate = 1000; //firerate in milliseconds
    public double CL_FireRate = FireRate;
    public Soldier Enemy;
    public Vector Direction = new Vector();
    public int Magazines = 4;
    public String Model = "Error";

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

    public boolean CanStillSeeEnemy(){
        if(Enemy == null){
            IsEnemyDetected = false;
            return false;
        }
        if(Enemy.IsDead() && Vector.GetVectorByPoints(Pos, Enemy.GetPos()).GetLength() < Fov){
            return true;
        }
        else{
            IsEnemyDetected = false;
            return false;
        }
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
            Random rand = new Random();
            Vector bulletUnit = Vector.GetVectorByPoints(Pos, target).Normalize();
            Vector normal = new Vector();
            normal.X = (-1) * bulletUnit.Y;
            normal.Y = bulletUnit.X;
            normal.Scale(accuracy);
            normal.Scale(rand.nextDouble() * 2 - 1);
            Bullet bullet = new Bullet(GetPos(),Vector.GetVectorByPoints(GetPos(), new Coord(GetPos().X + bulletUnit.X + normal.X, GetPos().Y + bulletUnit.Y + normal.Y)), Side);
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

    public boolean IsDead(){
        return isDead;
    }

    public void Kill(){
        StopMovement();
        IsEnemyDetected = false;
        Enemy = null;
        Model = "X";
        isDead = true;
    }
}