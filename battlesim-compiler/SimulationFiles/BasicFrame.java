package com.BattleSim;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Magnus on 17-03-2016.
 */


public class BasicFrame extends JFrame{

  public BasicFrame(Force force1, Force force2, Terrain terrain, Simulation force1Sim, Simulation force2Sim, ArrayList<Barrier> barriers){
      initUI(force1, force2, terrain, barriers, force1Sim, force2Sim);
  }

  private void initUI(Force force1, Force force2, Terrain terrain, ArrayList<Barrier> barriers, Simulation force1Sim, Simulation force2Sim){
      Map map = new Map(force1, force2, terrain, barriers, force1Sim, force2Sim);
      add(map);
      map.start();
      setTitle("BattleSim Simulation");
      setSize(terrain.Width, terrain.Height);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}
