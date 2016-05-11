package com.company;

import com.company.Objects.SimulationObjects.Force;
import com.company.Objects.SimulationObjects.Group;
import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.StaticObjects.Coord;
import com.company.Steps.MoveStep;
import com.company.Steps.WaitStep;

import java.util.HashMap;

/**
 * Created by Magnus on 11-05-2016.
 */
public class Defend extends Simulation {

    public Defend(HashMap<String, SimObj> simObjMap){
        super(simObjMap);
        Steps.add(new MoveStep(SimObjMap.get("enemies"), new Coord(950,250)));
        Steps.add(new MoveStep(SimObjMap.get("enemies"), new Coord(600,250)));
        Steps.add(new WaitStep(SimObjMap.get("enemies"), 60));
        Steps.add(new MoveStep(SimObjMap.get("enemies"), new Coord(300, 600)));
    }
}
