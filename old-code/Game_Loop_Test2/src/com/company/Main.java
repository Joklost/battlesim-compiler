package com.company;

import com.company.Objects.SimulationObjects.Force;
import com.company.Objects.SimulationObjects.Group;
import com.company.Objects.SimulationObjects.SimObj;
import com.company.Objects.SimulationObjects.Soldier;
import com.company.Objects.StaticObjects.Barrier;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Terrain;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

//http://zetcode.com/gfx/java2d/introduction/
public class Main {


    public static void main(String[] args) {
        Terrain terrain = new Terrain();
        terrain.width = 1800;
        terrain.height = 900;

        Barrier bar = new Barrier();
        bar.addVertex(new Coord(30, 50));
        bar.addVertex(new Coord(50, 30));
        bar.addVertex(new Coord(100, 100));
        bar.addVertex(new Coord(300, 600));
        Barrier bar2 = new Barrier();
        bar2.addVertex(new Coord(960, 800));
        bar2.addVertex(new Coord(900, 840));
        bar2.addVertex(new Coord(800, 890));
        bar2.addVertex(new Coord(950, 800));
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

        HashMap<String, SimObj> simObjMap = new HashMap<String, SimObj>();
        simObjMap.put("aSol1", aSol1);
        simObjMap.put("aSol2", aSol2);
        simObjMap.put("aSol3", aSol3);
        simObjMap.put("aSol4", aSol4);
        simObjMap.put("aSol5", aSol5);
        simObjMap.put("aSol6", aSol6);
        simObjMap.put("aGroup1", aGroup1);
        simObjMap.put("aGroup2", aGroup2);
        simObjMap.put("allies", allies);
        simObjMap.put("eSol1", eSol1);
        simObjMap.put("eSol2", eSol2);
        simObjMap.put("eSol3", eSol3);
        simObjMap.put("eGroup1", eGroup1);
        simObjMap.put("enemies", enemies);

        Simulation alliesSim = new ProtectTheGeneral(simObjMap);
        Simulation enemiesSim = new Defend(simObjMap);
        BasicFrame ex = new BasicFrame(allies, enemies, alliesSim, enemiesSim, terrain, barriers);
        ex.setVisible(true);

        /*BasicFrame ex2 = new BasicFrame(enemies, allies, enemiesSim, alliesSim, terrain, barriers);
        ex2.setVisible(true);
        EventQueue.invokeAndWait(new Runnable(){
            @Override
            public void run(){
                BasicFrame ex = new BasicFrame(allies, enemies, alliesSim, enemiesSim, terrain, barriers);
                ex.setVisible(true);
            }
        });
    }

    public static void runSimulation() {



        /*
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();

        instructions.add(new MoveInstruction(allies, new Coord(300,250)));
        instructions.add(new MoveInstruction(aGroup1, new Coord(600, 300)));
        instructions.add(new MoveInstruction(aGroup2, new Coord(600, 200)));

        instructions.add(new MoveInstruction(enemies, new Coord(950,250)));
        instructions.add(new MoveInstruction(enemies, new Coord(600,250)));
        instructions.add(new WaitInstruction(enemies, 60));
        instructions.add(new MoveInstruction(enemies, new Coord(300, 600)));
        */


    }
}
