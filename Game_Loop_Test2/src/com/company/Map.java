package com.company;

import com.company.Objects.Bullet;
import com.company.Objects.StaticObjects.*;
import com.company.Objects.SimulationObjects.*;
import com.company.Objects.StaticObjects.Vector;
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
    private long Elapsedtime = 0;
    private long HRT = 0;

    public static double FRAMERATE = 16; //Update interval in milliseconds
    public double TIMESCALE = 25;
    public ArrayList<Step> Steps = new ArrayList<Step>();
    public Force Force1 = new Force();
    public Force Force2 = new Force();
    public ArrayList<Barrier> Barriers;
    public ArrayList<Bullet> Bullets = new ArrayList<Bullet>();

    public Map(Force force1, Force force2, ArrayList<Step> steps, Terrain terrain, ArrayList<Barrier> barriers){
        Force1 = force1;
        Force2 = force2;
        Steps = steps;
        MapWidth = terrain.Width;
        MapHeight = terrain.Height;
        Barriers = barriers;
        initMap();
    }


    private void initMap() {
        setFocusable(true);
        timer = new Timer((int)FRAMERATE, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Elapsedtime = actionEvent.getWhen() - HRT;
        HRT = actionEvent.getWhen();
        updatePositions();
        repaint();
        performInstructions();
    }

    private void performInstructions() {
        for(Step step : Steps)
            step.RunIfCanStart();
    }

    private void updatePositions() {
        checkInterrupts();
        updateForcePositions(Force1);
        updateForcePositions(Force2);
    }

    private void updateForcePositions(Force force){
        for(Platoon p: force.Platoons){
            for(Group ga: p.Groups){
                for(Soldier s: ga.Soldiers){
                    s.Pos.NewPos(s.Direction, s.Velocity, (FRAMERATE / 1000) * TIMESCALE);
                }
            }
        }
    }



    private void checkInterrupts() {
        //
        StandardInterrupts.Check(Force1, Force2);

        ////Fire bullets
        Bullets.clear();
        fireBullets(Force1);
        fireBullets(Force2);
    }

    private void fireBullets(Force force){
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    if(s.IsEnemyDetected){
                        if(s.Ammo > 0)
                            Bullets.add(s.Shoot(s.Enemy.Pos));
                    }
                }
            }
        }
    }

    public void start(){

        timer.start();
    }

    private void doDrawing(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawString(Long.toString(Elapsedtime), MapWidth - 50, 15); //Render ElapsedTime between frames

        renderForce(g2d, Force1, "A");
        renderForce(g2d, Force2, "E");
        renderBarriers(g2d);
        renderBullets(g2d);

    }

    private void renderBullets(Graphics2D g2d) {
        for(Bullet b: Bullets){
            g2d.drawLine((int)b.FirePos.X, (int)b.FirePos.Y, (int)(b.FirePos.X + b.Vec.X), (int)(b.FirePos.Y + b.Vec.Y));
        }
    }

    private void renderBarriers(Graphics2D g2d) {
        for(Barrier b: Barriers){
            int i = 0;
            for(Coord c: b.Vertices) {
                if (i == b.Vertices.size() - 1) {
                    g2d.drawLine((int) b.Vertices.get(i).X, (int) b.Vertices.get(i).Y, (int) b.Vertices.get(0).X, (int) b.Vertices.get(0).Y);
                } else {
                    g2d.drawLine((int) b.Vertices.get(i).X, (int) b.Vertices.get(i).Y, (int) b.Vertices.get(i + 1).X, (int) b.Vertices.get(i + 1).Y);
                }
                i++;
            }
        }
    }

    private void renderForce(Graphics2D g2d, Force force, String str){
        for(Platoon p: force.Platoons){
            for(Group gr: p.Groups){
                for(Soldier s: gr.Soldiers){
                    g2d.drawString(str, (int)s.Pos.X, (int)s.Pos.Y);
                    if(s.IsEnemyDetected)
                        g2d.drawString("!", (int)s.Pos.X, (int)s.Pos.Y - 10);
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
