package com.company.Objects.DynamicObjects;

import com.company.DSTFunctions;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Group extends DynObj {
    public List<Soldier> Soldiers = new ArrayList<Soldier>();

    public Group(){

    }

    public void AddSoldiers(Soldier ... soldiers){
        for(Soldier s : soldiers)
            Soldiers.add(s);
    }

    public void Move(Coord target, double velocity){
        SetVelocity(velocity);
        SetDirection(DSTFunctions.FindUnitVector(DSTFunctions.CenterOfMass(this.GetCoordList()), target));
    }

    public void Move(Coord target){
        for(Soldier s : Soldiers){
            if(s.Velocity == 0){
                s.Velocity = s.DefaultVelocity;
            }
        }
        SetDirection(DSTFunctions.FindUnitVector(DSTFunctions.CenterOfMass(this.GetCoordList()), target));
    }

    public void SetVelocity(double velocity){
        for(Soldier s : Soldiers)
            s.Velocity = velocity;
    }

    public void SetDirection(Vector direction){
        for(Soldier s : Soldiers){
            s.Direction = direction;
        }
    }

    public List<Coord> GetCoordList(){
        List<Coord> res = new ArrayList<>();
        for(Soldier s : Soldiers){
            res.add(s.Pos);
        }
        return res;
    }

    public void StopMovement(){
        for(Soldier s : Soldiers)
            s.StopMovement();
    }

    public Coord GetPos(){
        return DSTFunctions.CenterOfMass(GetCoordList());
    }
}
