//package com.company;
package com.BattleSim;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 22-04-2016.
 */
class DSTFunctions{
    public static Vector FindUnitVector(Coord startCoord, Coord targetCoord){
        Vector res = new Vector();
        res.X = targetCoord.X - startCoord.X;
        res.Y = targetCoord.Y - startCoord.Y;
        double length = res.GetLength();
        res.X /= length;
        res.Y /= length;
        return res;
    }

    public static Coord CenterOfMass(List<Coord> coords){
        Coord res = new Coord();
        for(Coord coord : coords){
            res.X += coord.X;
            res.Y += coord.Y;
        }
        res.X /= coords.size();
        res.Y /= coords.size();

        return res;
    }
}

class Coord{
    public double X = 0;
    public double Y = 0;

    public Coord(){

    }
}

class Vector{
    public double X = 0;
    public double Y = 0;

    public Vector(){

    }

    public double GetLength(){
        return Math.abs(Math.sqrt( Math.pow(X, X) + Math.pow(Y, Y)));
    }
}

class Barrier{
    public List<Coord> Vertices;

    public void AddVertex(Coord vertex){
        this.Vertices.add(vertex);
    }
}

class Map{
    public int Width = 0;
    public int Height = 0;
}

class Soldier {
    public final double DefaultVelocity = 3; //Change this because this is a random number
    private int Size = 4;            //Change this because this is a random number

    public double Velocity = 0;
    public Vector Direction;
    public Coord SCoord;
    public int Magazines = 4;

    public void Move(Coord target){
        if(Velocity == 0){
            Velocity = DefaultVelocity;
        }
        Direction = DSTFunctions.FindUnitVector(SCoord, target);
    }

    public void Move(Coord target, double velocity){
        Velocity = velocity;
        Direction = DSTFunctions.FindUnitVector(SCoord, target);
    }

    public void StopMovement(){
        Velocity = 0;
    }

}

class Group{
    public List<Soldier> Soldiers;

    public void AddSoldiers(Soldier ... soldiers){
        for(Soldier s : soldiers)
            this.Soldiers.add(s);
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
            res.add(s.SCoord);
        }
        return res;
    }
}

class Platoon{
    public List<Group> Groups;

    public void AddGroups(Group ... groups){
        for(Group g : groups)
            this.Groups.add(g);
    }

    public void AddSoldiers(Soldier ... soldiers){
        Group nGroup = new Group();
        for(Soldier s: soldiers)
            nGroup.AddSoldiers(s);
        AddGroups(nGroup);
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
}

class Force{
    public List<Platoon> Platoons;

    public void AddPlatoons(Platoon ... platoons){
        for(Platoon p : platoons)
            this.Platoons.add(p);
    }

    public void AddGroups(Group ... groups){
        Platoon nPlat = new Platoon();

        for(Group g : groups)
            nPlat.AddGroups(g);
        AddPlatoons(nPlat);
    }

    public void AddSoldier(Soldier ... soldiers){
        Group nGroup = new Group();

        for(Soldier s : soldiers)
            nGroup.AddSoldiers(s);
        AddGroups(nGroup);
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
}