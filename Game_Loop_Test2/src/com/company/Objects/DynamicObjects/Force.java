package com.company.Objects.DynamicObjects;

import com.company.*;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 25-04-2016.
 */
public class Force extends DynObj {
    public List<Platoon> Platoons = new ArrayList<Platoon>();

    public Force(){

    }

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

    public void Move(Coord target){
        Move(target, Soldier.DefaultVelocity);
    }

    public void Move(Coord target, double velocity){
        for(Platoon p : Platoons)
            p.SetVelocity(velocity);
        SetDirection(DSTFunctions.FindUnitVector(DSTFunctions.CenterOfMass(GetCoordList()), target));
    }

    public void SetDirection(Vector direction){
        for(Platoon p : Platoons)
            p.SetDirection(direction);
    }

    public List<Coord> GetCoordList(){
        List<Coord> res = new ArrayList<>();
        for(Platoon p : Platoons)
            res.addAll(p.GetCoordList());
        return res;
    }

    public void StopMovement(){
        for(Platoon p: Platoons)
            p.StopMovement();
    }

    public Coord GetPos(){
        return DSTFunctions.CenterOfMass(GetCoordList());
    }
}
