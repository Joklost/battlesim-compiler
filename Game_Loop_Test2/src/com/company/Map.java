package com.company;

import com.company.Objects.StaticObjects.*;
import com.company.Objects.DynamicObjects.*;
import com.company.Steps.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Magnus on 17-03-2016.
 */
public class Map extends JPanel implements ActionListener {

    private int MapWidth = 300;
    private int MapHeight = 200;
    private Timer timer;
    private boolean isStarted;

    public ArrayList<Step> Steps = new ArrayList<Step>();

    public Force Force1 = new Force();
    public Force Force2 = new Force();

    public Map(Force force1, Force force2, ArrayList<Step> steps, Terrain terrain){
        Force1 = force1;
        Force2 = force2;
        Steps = steps;
        MapWidth = terrain.Width;
        MapHeight = terrain.Height;
        initMap();
    }


    private void initMap() {
        setFocusable(true);
        timer = new Timer(100, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        updatePositions();
        repaint();
        performInstructions();
    }

    private void performInstructions() {
        for(Step step : Steps)
            step.RunIfCanStart();
    }

    private void updatePositions() {
        for(Platoon p: Force1.Platoons){
            for(Group ga: p.Groups){
                for(Soldier s: ga.Soldiers){
                    s.Pos.NewPos(s.Direction, s.Velocity);
                }
            }
        }

        for(Platoon p: Force2.Platoons){
            for(Group ge: p.Groups){
                for(Soldier s: ge.Soldiers){
                    s.Pos.NewPos(s.Direction, s.Velocity);
                }
            }
        }
    }

    public void start(){

        timer.start();
    }

    private void doDrawing(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        /*for (Vehicle vehicle : this.vehicles) {
            g2d.drawString(vehicle.getModel(), vehicle.getPosX(), vehicle.getPosY());
        }*/
        for(Platoon p: Force1.Platoons){
            for(Group ga: p.Groups){
                for(Soldier s: ga.Soldiers){
                    g2d.drawString("A", ((int) s.Pos.X), ((int) s.Pos.Y));
                }
            }
        }

        for(Platoon p: Force2.Platoons){
            for(Group ge: p.Groups){
                for(Soldier s: ge.Soldiers){
                    g2d.drawString("E", ((int) s.Pos.X), ((int) s.Pos.Y));
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
}
