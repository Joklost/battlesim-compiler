package com.company;

import com.company.Objects.SimulationObjects.SimObj;
import com.company.Steps.Instruction;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Magnus on 11-05-2016.
 */
public abstract class Simulation {
    public ArrayList<Instruction> instructions = new ArrayList<Instruction>();
    public HashMap<String, SimObj> simObjMap = new HashMap<String, SimObj>();

    public Simulation(HashMap<String, SimObj> simObjMap){
        this.simObjMap = simObjMap;
    }

    public abstract void run(double deltaT);

    public abstract void runInterrupts(double deltaT);

}
