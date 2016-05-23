package com.BattleSim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Magnus on 11-05-2016.
 */
public abstract class Simulation {
    public ArrayList<Step> Steps = new ArrayList<Step>();
    public HashMap<String, SimObj> SimObjMap = new HashMap<String, SimObj>();

    public Simulation(HashMap<String, SimObj> simObjMap){
        this.SimObjMap = simObjMap;
    }

    public abstract void Run(double deltaT);

    public abstract void runInterrupts(double deltaT);
}
