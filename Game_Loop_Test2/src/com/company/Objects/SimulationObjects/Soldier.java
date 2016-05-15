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
    //Felter med bruger adgang til
    public Coord Pos = new Coord();
    public double Velocity = 0;
    public boolean IsDead(){
        return isDead;
    }
    //////////////////////
    
    private boolean isDead = false;
    private List<FireBulletListener> listeners = new ArrayList<FireBulletListener>();
    //private double accuracy = 0.0024999999999971; //accuracy er beregnet udfra at en hjemmeværnsmand skal kunne ramme en torso(0.5m bred) fra 200m afstand
    private double accuracy = 0.02;

    public int side = 0;
    public final static double DEFAULTVELOCITY = 2.2; //meter per second
    public double size = 0.25;            //radius of a soldier
    public boolean isEnemyDetected = false;
    public int fov = 50;
    public int ammo = 30;
    public int fireRate = 1000; //firerate in milliseconds
    public double cl_fireRate = fireRate;
    public Soldier enemy;
    public Vector direction = new Vector();
    public int magazines = 4;
    public String model = "Error";

    public Coord getPos(){
        return Pos;
    }

    public void move(Coord target){
        if(Velocity == 0){
            Velocity = DEFAULTVELOCITY;
        }
        direction = DSTFunctions.findUnitVector(Pos, target);
    }

    public void move(Coord target, double Velocity){
        this.Velocity = Velocity;
        direction = DSTFunctions.findUnitVector(Pos, target);
    }

    public void stopMovement(){
        Velocity = 0;
    }

    public void enemyDetected(Soldier enemy){
        this.enemy = enemy;
        isEnemyDetected = true;
    }

    public boolean canStillSeeEnemy(){
        if(enemy == null){
            isEnemyDetected = false;
            return false;
        }
        if(!enemy.IsDead() && Vector.getVectorByPoints(Pos, enemy.getPos()).getLength() < fov){
            return true;
        }
        else{
            isEnemyDetected = false;
            return false;
        }
    }

    public synchronized void addFireBulletListener( FireBulletListener l ) {
        listeners.add( l );
    }

    public synchronized void removeFireBulletListener( FireBulletListener l ) {
        listeners.remove( l );
    }

    private synchronized void _fireFireBulletEvent(Bullet bullet) {
        FireBulletEvent be = new FireBulletEvent( this, bullet );
        Iterator listeners = this.listeners.iterator();
        while( listeners.hasNext() ) {
            ( (FireBulletListener) listeners.next() ).bulletFired(be);
        }
    }

    public void TryShoot(Coord target){
        if(ammo > 0 && cl_fireRate >= fireRate){
            Random rand = new Random();
            Vector bulletUnit = Vector.getVectorByPoints(Pos, target).normalize();
            Vector normal = new Vector();
            normal.x = (-1) * bulletUnit.y;
            normal.y = bulletUnit.x;
            normal.scale(accuracy);
            normal.scale(rand.nextDouble() * 2 - 1);
            Bullet bullet = new Bullet(getPos(),Vector.getVectorByPoints(getPos(), new Coord(getPos().x + bulletUnit.x + normal.x, getPos().y + bulletUnit.y + normal.y)), side);
            _fireFireBulletEvent(bullet);
            ammo--;
            cl_fireRate = 0;
        }
    }

    public void serviceTimers(double deltaT){
        if(cl_fireRate < fireRate){
            cl_fireRate += deltaT;
        }
    }

    public void kill(){
        stopMovement();
        isEnemyDetected = false;
        enemy = null;
        model = "x";
        direction.scale(0);
        isDead = true;
    }
}
