package com.company;

import com.company.Objects.SimulationObjects.Force;
import com.company.Objects.StaticObjects.Barrier;
import com.company.Objects.StaticObjects.Terrain;
import com.company.Steps.Step;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Magnus on 17-03-2016.
 */


public class BasicFrame extends JFrame{

    public BasicFrame(Force force1, Force force2, Simulation force1Sim, Simulation force2Sim, Terrain terrain, ArrayList<Barrier> barriers){
        InitUI(force1, force2, force1Sim, force2Sim, terrain, barriers);
    }

    private void InitUI(Force force1, Force force2, Simulation force1Sim, Simulation force2Sim, Terrain terrain, ArrayList<Barrier> barriers){
        Map map = new Map(force1, force2, force1Sim, force2Sim, terrain, barriers);
        add(map);
        map.start();
        setTitle("Simple example");
        setSize(terrain.Width, terrain.Height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
