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
        steps.add(new MoveStep(simObjMap.get("allies"), new Coord(300,250)));
        steps.add(new MoveStep(simObjMap.get("aGroup1"), new Coord(600, 300)));
        steps.add(new MoveStep(simObjMap.get("aGroup2"), new Coord(600, 200)));
    }

    public void run(double deltaT){
        steps.get(0).runIfCanStart(deltaT);
        //potentielt neste steps i ifs
        steps.get(1).runIfCanStart(deltaT);
        steps.get(2).runIfCanStart(deltaT);
        steps.indexOf(new MoveStep(simObjMap.get("aGroup1"), new Coord(600, 300)));
        steps.
    }

}
