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
    public List<Group> Groups = new ArrayList<Group>();

    public void Take(Step controller){
        if(!IsControlled()){
            for(Group g: Groups){
                g.Take(controller);
            }
            Semaphor = true;
            Controller = controller;
        }
    }

    public void Release(){
        for(Group g: Groups){
            g.Release();
        }
        super.Release();
    }

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

    public void Move(Coord target){
        Move(target, Soldier.DefaultVelocity);
    }

    public void Move(Coord target, double velocity){
        for(Group g : Groups){
            g.SetVelocity(velocity);
        }
        SetDirection(DSTFunctions.FindUnitVector(DSTFunctions.CenterOfMass(this.GetCoordList()), target));
    }

    public void SetVelocity(double velocity){
        for(Group g : Groups)
            g.SetVelocity(velocity);
    }

    public void SetDirection(Vector direction){
        for(Group g : Groups){
            g.SetDirection(direction);
        }
    }

    public List<Coord> GetCoordList(){
        List<Coord> res = new ArrayList<>();
        for(Group g : Groups)
            res.addAll(g.GetCoordList());
        return res;
    }

    public void StopMovement(){
        for(Group g : Groups)
            g.StopMovement();
    }

    public Coord GetPos(){
        return DSTFunctions.CenterOfMass(GetCoordList());
    }
}
