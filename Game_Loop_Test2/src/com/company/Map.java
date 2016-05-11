package com.company;

import com.company.Objects.Bullet;
import com.company.Objects.StaticObjects.*;
import com.company.Objects.SimulationObjects.*;
import com.company.Objects.StaticObjects.Vector;
import com.company.Steps.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Magnus on 17-03-2016.
 */
public class Map extends JPanel implements ActionListener, FireBulletListener, ChangeListener {
    private static final int TS_MIN = 1;
    private static final int TS_MAX = 25;
    private static final int TS_INIT = 10; //Initial timescale
    private static final int ALLY = 0;
    private static final int ENEMY = 1;
    private int MapWidth = 300;
    private int MapHeight = 200;
    private Timer timer;
    private boolean isStarted;
    private long Elapsedtime = 0;
    private long HRT = 0; //High Resolution Timer

    public static double FRAMERATE = 33; //Update interval in milliseconds
    public double TIMESCALE = TS_INIT;
    public double deltaT = FRAMERATE * TIMESCALE; //deltaT is in milliseconds
    public Simulation Force1Sim;
    public Simulation Force2Sim;
    public Force Force1 = new Force();
    public Force Force2 = new Force();
    public ArrayList<Barrier> Barriers;
    public ArrayList<Bullet> Bullets = new ArrayList<Bullet>();

    public Map(Force force1, Force force2, Simulation force1Sim, Simulation force2Sim, Terrain terrain, ArrayList<Barrier> barriers){
        Force1 = force1;
        Force2 = force2;
        Force1Sim = force1Sim;
        Force2Sim = force2Sim;
        MapWidth = terrain.Width;
        MapHeight = terrain.Height;
        Barriers = barriers;
        initEventListeners(Force1);
        initEventListeners(Force2);
        initSide(Force1, ALLY);
        initSide(Force2, ENEMY);
        initSlider();
        initModels(Force1, "A");
        initModels(Force2, "E");
        initMap();
    }

    private void initSide(Force force, int side) {
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    s.Side = side;
                }
            }
        }
    }

    private void initModels(Force force, String model) {
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    s.Model = model;
                }
            }
        }
    }

    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/SliderDemoProject/src/components/SliderDemo.java
    private void initSlider() {
        JSlider timeScale = new JSlider(JSlider.HORIZONTAL, TS_MIN, TS_MAX, TS_INIT );
        timeScale.setMajorTickSpacing(5);
        timeScale.setMinorTickSpacing(1);
        timeScale.setPaintTicks(true);
        timeScale.setPaintLabels(true);
        timeScale.addChangeListener(this);
        timeScale.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        timeScale.setFont(font);
        add(timeScale);
    }

    private void initEventListeners(Force force) {
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    s.addFireBulletListener(this);
                }
            }
        }
    }

    private void initMap() {
        setFocusable(true);
        timer = new Timer((int)FRAMERATE, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Elapsedtime = actionEvent.getWhen() - HRT;
        HRT = actionEvent.getWhen();
        updateStates();
        detectCollisions();
        performInstructions();
        repaint();
    }

    private void performInstructions() {
        Force1Sim.Run(deltaT);
        Force2Sim.Run(deltaT);
    }

    private void updateStates() {
        updateForceStates(Force1);
        updateForceStates(Force2);
    }

    private void updateForceStates(Force force){
        for(Platoon p: force.Platoons){
            for(Group ga: p.Groups){
                for(Soldier s: ga.Soldiers){
                    for(Bullet b: Bullets){
                        if(b.Owner != s.Side){
                            Vector bulToSol = Vector.GetVectorByPoints(b.FirePos, s.GetPos());
                            Vector projection = bulToSol.Dot(b.Vec.Normalize());
                            Vector dist = Vector.GetVectorByPoints(new Coord(b.FirePos.X + projection.X, b.FirePos.Y + projection.Y), s.GetPos());
                            if(dist.GetLength() < s.Size){
                                s.Kill();
                            }
                        }

                    }

                    s.Pos.NewPos(s.Direction, s.Velocity, deltaT / 1000);
                    s.serviceTimers(deltaT);
                }
            }
        }
    }

    private void detectCollisions() {
        CollisionDetector.VisualDetect(Force1, Force2);
        ////Fire bullets
        Bullets.clear();
        fireBullets(Force1);
        fireBullets(Force2);
    }

    private void fireBullets(Force force){
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    if(s.IsEnemyDetected && !s.Enemy.IsDead()){
                        s.TryShoot(s.Enemy.GetPos());
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

        renderForce(g2d, Force1);
        renderForce(g2d, Force2);
        renderBarriers(g2d);
        renderBullets(g2d);

    }

    private void renderBullets(Graphics2D g2d) {
        for(Bullet b: Bullets){
            g2d.drawLine((int)b.FirePos.X, (int)b.FirePos.Y, (int)(b.FirePos.X + b.Vec.X) , (int)(b.FirePos.Y + b.Vec.Y));
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

    private void renderForce(Graphics2D g2d, Force force){
        for(Platoon p: force.Platoons){
            for(Group gr: p.Groups){
                for(Soldier s: gr.Soldiers){
                    g2d.drawString(s.Model, (int)s.Pos.X, (int)s.Pos.Y);
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

    @Override
    public void BulletFired(FireBulletEvent event) {
        Bullets.add(event.GetBullet());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int ts = (int)source.getValue();
            deltaT = FRAMERATE  * ts;
        }
    }
}
