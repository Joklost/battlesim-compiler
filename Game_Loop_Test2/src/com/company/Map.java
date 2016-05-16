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
    private static String ALLYSTRING = "A";
    private static String ENEMYSTRING = "E";
    private int mapWidth = 300;
    private int mapHeight = 200;
    private Timer timer;
    private boolean isStarted;
    private long elapsedTime = 0;
    private long hrt = 0; //High Resolution Timer
    private long frameNum = 0;

    public static int FRAMEINTERVAL = 33; //Update interval in milliseconds
    public double timeScale = TS_INIT;
    public double deltaT = FRAMEINTERVAL * timeScale; //deltaT is in milliseconds
    public Simulation force1Sim;
    public Simulation force2Sim;
    public Force force1 = new Force();
    public Force force2 = new Force();
    public ArrayList<Barrier> barriers;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public Map(Force f1, Force f2, Simulation f1Sim, Simulation f2Sim, Terrain terrain, ArrayList<Barrier> bars){
        this.force1 = f1;
        this.force2 = f2;
        this.force1Sim = f1Sim;
        this.force2Sim = f2Sim;
        mapWidth = terrain.width;
        mapHeight = terrain.height;
        this.barriers = bars;
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
                    s.side = side;
                }
            }
        }
    }

    private void initModels(Force force, String model) {
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    s.model = model;
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
        elapsedTime = actionEvent.getWhen() - hrt;
        hrt = actionEvent.getWhen();
        updateStates();
        performInstructions();
        detectCollisions();
        repaint();
        frameNum++;
    }

    private void performInstructions() {
        force1Sim.run(deltaT);
        force2Sim.run(deltaT);
    }

    private void updateStates() {
        updateForceStates(force1);
        updateForceStates(force2);
    }

    private void updateForceStates(Force force){
        for(Platoon p: force.Platoons){
            for(Group ga: p.Groups){
                for(Soldier s: ga.Soldiers){
                    for(Bullet b: bullets){
                        if(b.owner != s.side){
                            Vector bulToSol = Vector.getVectorByPoints(b.firePos, s.getPos());
                            Vector projection = b.vector.normalize();
                            double projLength = bulToSol.dot(b.vector.normalize());
                            projection.scale(projLength);
                            Vector dist = Vector.getVectorByPoints(new Coord(b.firePos.x + projection.x, b.firePos.y + projection.y), s.getPos());
                            if(dist.getLength() < s.size){
                                s.kill();
                            }
                        }

                    }
                    if(!s.IsDead()){
                        s.Pos.newPos(s.direction, s.Velocity, deltaT / 1000);
                        s.serviceTimers(deltaT);
                    }
                }
            }
        }
    }

    private void detectCollisions() {
        CollisionDetector.visualDetect(force1, force2);
        ////Fire bullets
        bullets.clear();
        firebullets(force1);
        firebullets(force2);
    }

    private void firebullets(Force force){
        for(Platoon p: force.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    if(s.isEnemyDetected && !s.enemy.IsDead()){
                        s.stopMovement();
                        s.TryShoot(s.enemy.getPos());
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
        g2d.drawString(Long.toString(elapsedTime), mapWidth - 50, 15); //Render ElapsedTime between frames
        renderForce(g2d, force1);
        renderForce(g2d, force2);
        renderBarriers(g2d);
        renderbullets(g2d);
    }

    private void renderbullets(Graphics2D g2d) {
        for(Bullet b: bullets){
            g2d.drawLine((int)b.firePos.x, mapHeight - (int)b.firePos.y, (int)(b.firePos.x + b.vector.x) ,  mapHeight - (int)(b.firePos.y + b.vector.y));
        }
    }

    private void renderBarriers(Graphics2D g2d) {
        for(Barrier b: barriers){
            int i = 0;
            for(Coord c: b.vertices) {
                if (i == b.vertices.size() - 1) {
                    g2d.drawLine((int) b.vertices.get(i).x,  mapHeight - (int) b.vertices.get(i).y, (int) b.vertices.get(0).x,  mapHeight - (int) b.vertices.get(0).y);
                } else {
                    g2d.drawLine((int) b.vertices.get(i).x, mapHeight - (int) b.vertices.get(i).y, (int) b.vertices.get(i + 1).x, mapHeight - (int) b.vertices.get(i + 1).y);
                }
                i++;
            }
        }
    }

    private void renderForce(Graphics2D g2d, Force force){
        for(Platoon p: force.Platoons){
            for(Group gr: p.Groups){
                for(Soldier s: gr.Soldiers){
                    g2d.drawString(s.model, (int)s.Pos.x, mapHeight - (int)s.Pos.y);
                    if(s.isEnemyDetected)
                        g2d.drawString("!", (int)s.Pos.x, mapHeight - (int)s.Pos.y - 10);
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
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int ts = (int)source.getValue();
            deltaT = FRAMEINTERVAL  * ts;
        }
    }

    @Override
    public void bulletFired(FireBulletEvent event) {
        bullets.add(event.getBullet());
    }
}
