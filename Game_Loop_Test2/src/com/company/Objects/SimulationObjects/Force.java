package com.company.Objects.SimulationObjects;

import com.company.*;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;
import com.company.Steps.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Force extends SimObj {
    ///////////Felter og funtioner med bruger adgang til
    public List<Platoon> Platoons = new ArrayList<Platoon>();
    public void AddPlatoons(Platoon ... platoons){
        for(Platoon p : platoons)
            this.Platoons.add(p);
    }
    public void AddGroups(Group... groups){
        Platoon nPlat = new Platoon();

        for(Group g : groups)
            nPlat.AddGroups(g);
        AddPlatoons(nPlat);
    }
    public void AddSoldier(Soldier... soldiers){
        Group nGroup = new Group();

        for(Soldier s : soldiers)
            nGroup.AddSoldiers(s);
        AddGroups(nGroup);
    }

    public boolean IsDead(){
        for(Platoon p: Platoons){
            if(!p.IsDead()){
                return false;
            }
        }
        return true;
    }
    ///////////////

    public void take(Step controller){
        if(!isControlled()){
            for(Platoon p: Platoons){
                p.take(controller);
            }
            semaphor = true;
            this.controller = controller;
        }
    }

    public void release(){
        for(Platoon p: Platoons){
            p.release();
        }
        super.release();
    }

    public void move(Coord target){
        move(target, Soldier.DEFAULTVELOCITY);
    }

    public void move(Coord target, double velocity){
        for(Platoon p : Platoons)
            p.setVelocity(velocity);
        setDirection(DSTFunctions.findUnitVector(DSTFunctions.centerOfMass(getAliveSoldiersCoords()), target));
    }

    public void setDirection(Vector direction){
        for(Platoon p : Platoons)
            p.setDirection(direction);
    }

    public List<Coord> getCoordList(){
        List<Coord> res = new ArrayList<>();
        for(Platoon p : Platoons)
            res.addAll(p.getCoordList());
        return res;
    }

    public List<Coord> getAliveSoldiersCoords(){
        List<Coord> res = new ArrayList<>();
        for(Platoon p : Platoons)
            res.addAll(p.getAliveSoldiersCoords());
        return res;
    }

    public void stopMovement(){
        for(Platoon p: Platoons)
            p.stopMovement();
    }

    public Coord getPos(){
        return DSTFunctions.centerOfMass(getAliveSoldiersCoords());
    }

}
