package com.BattleSim;

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
    private static String ALLYSTRING = "A";
    private static String ENEMYSTRING = "E";


    private int mapWidth = 300;
    private int mapHeight = 200;
    private Timer timer;
    private boolean isStarted;
    private long elapsedTime = 0;
    private long HRT = 0; //High Resolution Timer
    private long frameNum = 0;

    public static int FRAMEINTERVAL = 33; //Update interval in milliseconds

    public double timeScale = TS_INIT;
    public double deltaT = FRAMEINTERVAL * timeScale; //deltaT is in milliseconds
    public ArrayList<Step> Steps = new ArrayList<Step>();
    public Force force1 = new Force();
    public Force force2 = new Force();
    public Simulation force1Sim;
    public Simulation force2Sim;
    public ArrayList<Barrier> Barriers;
    public ArrayList<Bullet> Bullets = new ArrayList<Bullet>();

    public Map(Force f1, Force f2, Terrain terrain, ArrayList<Barrier> bars, Simulation f1Sim, Simulation f2Sim){
        force1 = f1;
        force2 = f2;
        force1Sim = f1Sim;
        force2Sim = f2Sim;
        mapWidth = terrain.Width;
        mapHeight = terrain.Height;
        Barriers = bars;
        initEventListeners(force1);
        initEventListeners(force2);
        initSide(force1, ALLY);
        initSide(force2, ENEMY);
        initSlider();
        initModels(force1, ALLYSTRING);
        initModels(force2, ENEMYSTRING);
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
        timer = new Timer(FRAMEINTERVAL, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        elapsedTime = actionEvent.getWhen() - HRT;
        HRT = actionEvent.getWhen();
        updateStates();
        performInstructions();
        detectCollisions();
        repaint();
        frameNum++;
    }

    private void performInstructions() {
        //Her skal der blot kaldes MySimulation.Run() for hver af sidernes simulation
        force1Sim.Run(deltaT);
        force2Sim.Run(deltaT);
    }

    private void updateStates() {
        updateForceStates(force1);
        updateForceStates(force2);
        ////Fire bullets
        Bullets.clear();
        fireBullets(force1);
        fireBullets(force2);
    }

    private void updateForceStates(Force force){
        for(Platoon p: force.Platoons){
            for(Group ga: p.Groups){
                for(Soldier s: ga.Soldiers){
                    if(!s.IsDead()){
                        s.Pos.NewPos(s.Direction, s.Velocity, deltaT / 1000);
                        s.serviceTimers(deltaT);
                    }
                }
            }
        }
    }

    private void detectCollisions() {
        CollisionDetector.VisualDetect(force1, force2, Bullets);
    }

    private void fireBullets(Force force){
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    if(s.IsEnemyDetected && !s.Enemy.IsDead()){
                        s.StopMovement();
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

        double scale = 1;
        g2d.translate(mapWidth/2, mapHeight/2);
        g2d.scale(scale, scale);
        g2d.translate(-mapWidth/2, -mapHeight/2);
        g2d.setPaint(Color.LIGHT_GRAY);
        g2d.fillRect(0,0,mapWidth,mapHeight);
        g2d.setPaint(Color.BLACK);
        g2d.drawString("Frame interval: " + elapsedTime + "ms", mapWidth - 250, 15);//Render ElapsedTime between frames
        g2d.drawString("Simulation time frame interval: " + deltaT + "ms", mapWidth - 250, 25);
        g2d.drawString("FPS: " + (int)(1/ (((float) elapsedTime) / 1000)), mapWidth - 250, 35);
        g2d.drawString("Frame number: " + frameNum, mapWidth - 250, 45);

        renderForce(g2d, force1);
        renderForce(g2d, force2);
        renderBarriers(g2d);
        renderBullets(g2d);

    }

    private void renderBullets(Graphics2D g2d) {
        for(Bullet b: Bullets){
            g2d.drawLine((int)b.FirePos.X, mapHeight - (int)b.FirePos.Y, (int)(b.FirePos.X + b.Vec.X) , mapHeight - (int)(b.FirePos.Y + b.Vec.Y));
        }
    }

    private void renderBarriers(Graphics2D g2d) {
        for(Barrier b: Barriers){
            int i = 0;
            for(Coord c: b.Vertices) {
                if (i == b.Vertices.size() - 1) {
                    g2d.drawLine((int) b.Vertices.get(i).X, mapHeight - (int) b.Vertices.get(i).Y, (int) b.Vertices.get(0).X, mapHeight - (int) b.Vertices.get(0).Y);
                } else {
                    g2d.drawLine((int) b.Vertices.get(i).X, mapHeight - (int) b.Vertices.get(i).Y, (int) b.Vertices.get(i + 1).X, mapHeight - (int) b.Vertices.get(i + 1).Y);
                }
                i++;
            }
        }
    }

    private void renderForce(Graphics2D g2d, Force force){
        for(Platoon p: force.Platoons){
            for(Group gr: p.Groups){
                for(Soldier s: gr.Soldiers){
                    g2d.drawString(s.Model, (int)s.Pos.X, mapHeight - (int)s.Pos.Y);
                    if(s.IsEnemyDetected)
                        g2d.drawString("!", (int)s.Pos.X, mapHeight - (int)s.Pos.Y - 10);
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
            deltaT = FRAMEINTERVAL  * ts;
        }
    }
}
