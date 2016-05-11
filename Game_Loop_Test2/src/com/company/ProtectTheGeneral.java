package com.company;

import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.StaticObjects.Coord;
import com.company.Steps.MoveStep;

import java.util.HashMap;

/**
 * Created by Magnus on 11-05-2016.
 */
public class ProtectTheGeneral extends Simulation{

    public ProtectTheGeneral(HashMap<String, SimObj> simObjMap){
        super(simObjMap);
        Steps.add(new MoveStep(SimObjMap.get("allies"), new Coord(300,250)));
        Steps.add(new MoveStep(SimObjMap.get("aGroup1"), new Coord(600, 300)));
        Steps.add(new MoveStep(SimObjMap.get("aGroup2"), new Coord(600, 200)));
    }

    public void Run(double deltaT){
        Steps.get(0).RunIfCanStart(deltaT);
        //potentielt neste steps i ifs
        Steps.get(1).RunIfCanStart(deltaT);
        Steps.get(2).RunIfCanStart(deltaT);
    }

}
