package com.company;

import com.company.Objects.SimulationObjects.Force;
import com.company.Objects.SimulationObjects.Group;
import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.StaticObjects.Coord;
import com.company.Steps.MoveStep;
import com.company.Steps.Step;
import com.company.Steps.WaitStep;

import java.util.HashMap;

/**
 * Created by Magnus on 11-05-2016.
 */
public class Defend extends Simulation {

    public Defend(HashMap<String, SimObj> simObjMap){
        super(simObjMap);
        steps.add(new MoveStep(simObjMap.get("enemies"), new Coord(950,250)));
        steps.add(new MoveStep(simObjMap.get("enemies"), new Coord(600,250)));
        steps.add(new WaitStep(simObjMap.get("enemies"), 60));
        steps.add(new MoveStep(simObjMap.get("enemies"), new Coord(300, 600)));
    }

    @Override
    public void run(double deltaT) {
        for(Step step: steps){
            step.runIfCanStart(deltaT);
        }
    }
}
