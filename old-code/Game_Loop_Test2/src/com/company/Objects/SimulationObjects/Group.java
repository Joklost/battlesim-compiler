package com.company.Objects.SimulationObjects;

import com.company.DSTFunctions;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;
import com.company.Instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Group extends SimObj {
    ///////////Felter og funtioner med bruger adgang til
    public List<Soldier> Soldiers = new ArrayList<Soldier>();
    public void AddSoldiers(Soldier ... soldiers){
        for(Soldier s : soldiers)
            Soldiers.add(s);
    }

    public boolean IsDead(){
        for(Soldier s: Soldiers){
            if(!s.IsDead()){
                return false;
            }
        }
        return true;
    }

    public int CountAliveSoldiers(){
        int res = 0;
        for(Soldier s: Soldiers){
            res += s.CountAliveSoldiers();
        }
        return res;
    }
    //////////

    public void take(Instruction controller){
        for(Soldier s: Soldiers){
            s.take(controller);
        }
        semaphor = true;
        this.controller = controller;
    }

    public void release(){
        for(Soldier s: Soldiers){
            s.release();
        }
        super.release();
    }


    public void setVector(Coord target, double velocity){
        setVelocity(velocity);
        setDirection(DSTFunctions.findUnitVector(DSTFunctions.centerOfMass(this.getAliveSoldiersCoords()), target));
    }

    public void setVector(Coord target){
        for(Soldier s : Soldiers){
            if(s.Velocity == 0){
                s.Velocity = s.DEFAULTVELOCITY;
            }
        }
        setDirection(DSTFunctions.findUnitVector(DSTFunctions.centerOfMass(this.getAliveSoldiersCoords()), target));
    }

    public void setVelocity(double velocity){
        for(Soldier s : Soldiers)
            s.Velocity = velocity;
    }

    public void setDirection(Vector direction){
        for(Soldier s : Soldiers){
            s.direction = direction;
        }
    }

    public List<Coord> getCoordList(){
        List<Coord> res = new ArrayList<>();
        for(Soldier s : Soldiers){
            res.add(s.getPos());
        }
        return res;
    }

    public List<Coord> getAliveSoldiersCoords(){
        List<Coord> res = new ArrayList<>();
        for(Soldier s : Soldiers){
            if(!s.IsDead()){
                res.add(s.getPos());
            }
        }
        return res;
    }

    public void stopMovement(){
        for(Soldier s : Soldiers)
            s.stopMovement();
    }

    public Coord getPos(){
        return DSTFunctions.centerOfMass(getAliveSoldiersCoords());
    }
}
