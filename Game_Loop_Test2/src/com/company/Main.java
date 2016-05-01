package com.company;

import com.company.Objects.DynamicObjects.Force;
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

    public static int MAX_X = 300;
    public static int MAX_Y = 200;

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
        terrain.Width = 300;
        terrain.Height = 200;

        Soldier aSol = new Soldier();
        aSol.Pos = new Coord(100,100);
        Force allies = new Force();

        Soldier eSol = new Soldier();
        eSol.Pos = new Coord(50,50);
        Force enemies = new Force();

        allies.AddSoldier(aSol);
        enemies.AddSoldier(eSol);

        ArrayList<Step> steps = new ArrayList<Step>();


        steps.add(new MoveStep(allies, new Coord(50,50)));
        steps.add(new MoveStep(allies, new Coord(190,60)));

        steps.add(new MoveStep(enemies, new Coord(100,100)));
        steps.add(new MoveStep(enemies, new Coord(150,150)));



        BasicFrame ex = new BasicFrame(allies, enemies, steps, terrain);
        ex.setVisible(true);
    }
}
