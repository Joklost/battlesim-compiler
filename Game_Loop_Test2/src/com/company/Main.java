package com.company;

import com.company.Objects.SimulationObjects.Force;
import com.company.Objects.SimulationObjects.Group;
import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.SimulationObjects.Soldier;
import com.company.Objects.StaticObjects.Barrier;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Terrain;
import com.company.Steps.MoveStep;
import com.company.Steps.Step;
import com.company.Steps.WaitStep;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {


    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                runSimulation();
            }
        });
    }

    public static void runSimulation() {

        Terrain terrain = new Terrain();
        terrain.Width = 1800;
        terrain.Height = 900;

        Barrier bar = new Barrier();
        bar.AddVertex(new Coord(30, 50));
        bar.AddVertex(new Coord(50, 30));
        bar.AddVertex(new Coord(100, 100));
        bar.AddVertex(new Coord(300, 600));
        Barrier bar2 = new Barrier();
        bar2.AddVertex(new Coord(960, 800));
        bar2.AddVertex(new Coord(900, 840));
        bar2.AddVertex(new Coord(800, 890));
        bar2.AddVertex(new Coord(950, 800));
        ArrayList<Barrier> barriers = new ArrayList<Barrier>();
        barriers.add(bar);
        barriers.add(bar2);

        Soldier aSol1 = new Soldier();
        aSol1.Pos = new Coord(30,30);
        Soldier aSol2 = new Soldier();
        aSol2.Pos = new Coord(38, 30);
        Soldier aSol3 = new Soldier();
        aSol3.Pos = new Coord(34, 38);
        Group aGroup1 = new Group();
        aGroup1.AddSoldiers(aSol1, aSol2, aSol3);
        Soldier aSol4 = new Soldier();
        aSol4.Pos = new Coord(40,30);
        Soldier aSol5 = new Soldier();
        aSol5.Pos = new Coord(48, 30);
        Soldier aSol6 = new Soldier();
        aSol6.Pos = new Coord(44, 38);
        Group aGroup2 = new Group();
        aGroup2.AddSoldiers(aSol4, aSol5, aSol6);
        Force allies = new Force();
        allies.AddGroups(aGroup1, aGroup2);

        Soldier eSol1 = new Soldier();
        eSol1.Pos = new Coord(960,30);
        Soldier eSol2 = new Soldier();
        eSol2.Pos = new Coord(968,30);
        Soldier eSol3 = new Soldier();
        eSol3.Pos = new Coord(964,38);
        Group eGroup1 = new Group();
        eGroup1.AddSoldiers(eSol1, eSol2, eSol3);
        Force enemies = new Force();
        enemies.AddGroups(eGroup1);

        HashMap<String, SimObj> SimObjMap = new HashMap<String, SimObj>();
        SimObjMap.put("aSol1", aSol1);
        SimObjMap.put("aSol2", aSol2);
        SimObjMap.put("aSol3", aSol3);
        SimObjMap.put("aSol4", aSol4);
        SimObjMap.put("aSol5", aSol5);
        SimObjMap.put("aSol6", aSol6);
        SimObjMap.put("aGroup1", aGroup1);
        SimObjMap.put("aGroup2", aGroup2);
        SimObjMap.put("allies", allies);
        SimObjMap.put("eSol1", eSol1);
        SimObjMap.put("eSol2", eSol2);
        SimObjMap.put("eSol3", eSol3);
        SimObjMap.put("eGroup1", eGroup1);
        SimObjMap.put("enemies", enemies);

        /*
        ArrayList<Step> steps = new ArrayList<Step>();

        steps.add(new MoveStep(allies, new Coord(300,250)));
        steps.add(new MoveStep(aGroup1, new Coord(600, 300)));
        steps.add(new MoveStep(aGroup2, new Coord(600, 200)));

        steps.add(new MoveStep(enemies, new Coord(950,250)));
        steps.add(new MoveStep(enemies, new Coord(600,250)));
        steps.add(new WaitStep(enemies, 60));
        steps.add(new MoveStep(enemies, new Coord(300, 600)));
        */
        Simulation alliesSim = new ProtectTheGeneral(SimObjMap);
        Simulation enemiesSim = new Defend(SimObjMap);
        BasicFrame ex = new BasicFrame(allies, enemies, alliesSim, enemiesSim, terrain, barriers);
        ex.setVisible(true);
    }
}
