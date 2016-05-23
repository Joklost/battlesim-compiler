package com.company;

import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.StaticObjects.Coord;
import com.company.Steps.MoveInstruction;
import com.company.Steps.WaitInstruction;

import java.util.HashMap;

/**
 * Created by Magnus on 11-05-2016.
 */
public class ProtectTheGeneral extends Simulation{

    public ProtectTheGeneral(HashMap<String, SimObj> simObjMap){
        super(simObjMap);
        instructions.add(new MoveInstruction(simObjMap.get("allies"), new Coord(300,250)));
        instructions.add(new MoveInstruction(simObjMap.get("aGroup1"), new Coord(600, 300)));
        instructions.add(new MoveInstruction(simObjMap.get("aGroup2"), new Coord(600, 200)));
        instructions.add(new WaitInstruction(simObjMap.get("allies"), 10));
        instructions.add(new MoveInstruction(simObjMap.get("allies"), new Coord(1000, 800)));
    }

    public void run(double deltaT){
        instructions.get(0).runIfCanStart(deltaT);
        //potentielt neste instructions i ifs
        instructions.get(1).runIfCanStart(deltaT);
        instructions.get(2).runIfCanStart(deltaT);
    }

    public void runInterrupts(double deltaT){
        if(simObjMap.get("enemies").IsDead()){
            instructions.get(3).interrupt(deltaT);
            instructions.get(4).interrupt(deltaT);
        }
    }
}
