package com.company;

import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.StaticObjects.Coord;
import com.company.Steps.MoveInstruction;
import com.company.Steps.Instruction;
import com.company.Steps.WaitInstruction;

import java.util.HashMap;

/**
 * Created by Magnus on 11-05-2016.
 */
public class Defend extends Simulation {

    public Defend(HashMap<String, SimObj> simObjMap){
        super(simObjMap);
        instructions.add(new MoveInstruction(simObjMap.get("enemies"), new Coord(950,250)));
        instructions.add(new MoveInstruction(simObjMap.get("enemies"), new Coord(600,250)));
        instructions.add(new WaitInstruction(simObjMap.get("enemies"), 60));
        instructions.add(new MoveInstruction(simObjMap.get("enemies"), new Coord(300, 600)));
    }

    @Override
    public void run(double deltaT) {
        for(Instruction instruction : instructions){
            instruction.runIfCanStart(deltaT);
        }
    }

    public void runInterrupts(double deltaT){

    }
}
