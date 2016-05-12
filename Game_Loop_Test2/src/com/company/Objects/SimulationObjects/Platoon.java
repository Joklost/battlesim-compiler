package com.company.Objects.SimulationObjects;

import com.company.DSTFunctions;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;
import com.company.Steps.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Platoon extends SimObj {
    ///////////Felter og funtioner med bruger adgang til
    public List<Group> Groups = new ArrayList<Group>();
    public void AddGroups(Group ... groups){
        for(Group g : groups)
            this.Groups.add(g);
    }
    public void AddSoldiers(Soldier... soldiers){
        Group nGroup = new Group();
        for(Soldier s: soldiers)
            nGroup.AddSoldiers(s);
        AddGroups(nGroup);
    }

    public boolean IsDead(){
        for(Group g: Groups){
            if(!g.IsDead()){
                return false;
            }
        }
        return true;
    }
    //////////


    public void take(Step controller){
        if(!isControlled()){
            for(Group g: Groups){
                g.take(controller);
            }
            semaphor = true;
            controller = controller;
        }
    }

    public void release(){
        for(Group g: Groups){
            g.release();
        }
        super.release();
    }

    public void move(Coord target){
        move(target, Soldier.DEFAULTVELOCITY);
    }

    public void move(Coord target, double velocity){
        for(Group g : Groups){
            g.setVelocity(velocity);
        }
        setDirection(DSTFunctions.findUnitVector(DSTFunctions.centerOfMass(this.getCoordList()), target));
    }

    public void setVelocity(double velocity){
        for(Group g : Groups)
            g.setVelocity(velocity);
    }

    public void setDirection(Vector direction){
        for(Group g : Groups){
            g.setDirection(direction);
        }
    }

    public List<Coord> getCoordList(){
        List<Coord> res = new ArrayList<>();
        for(Group g : Groups)
            res.addAll(g.getCoordList());
        return res;
    }

    public void stopMovement(){
        for(Group g : Groups)
            g.stopMovement();
    }

    public Coord getPos(){
        return DSTFunctions.centerOfMass(getCoordList());
    }
}
