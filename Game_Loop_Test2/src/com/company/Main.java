package com.company;

import com.company.Objects.DynamicObjects.Force;
import com.company.Objects.DynamicObjects.Group;
import com.company.Objects.DynamicObjects.Soldier;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Terrain;
import com.company.Steps.MoveStep;
import com.company.Steps.Step;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


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
        terrain.Width = 1920;
        terrain.Height = 1080;

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

        ArrayList<Step> steps = new ArrayList<Step>();

        steps.add(new MoveStep(allies, new Coord(300,250)));
        steps.add(new MoveStep(aGroup1, new Coord(600, 300)));
        steps.add(new MoveStep(aGroup2, new Coord(600, 200)));

        steps.add(new MoveStep(enemies, new Coord(950,250)));
        steps.add(new MoveStep(enemies, new Coord(600,250)));

        BasicFrame ex = new BasicFrame(allies, enemies, steps, terrain);
        ex.setVisible(true);
    }
}