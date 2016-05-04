package com.company;

import com.company.Objects.Bullet;
import com.company.Objects.StaticObjects.*;
import com.company.Objects.SimulationObjects.*;
import com.company.Steps.*;
import com.sun.xml.internal.ws.api.message.Message;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Magnus on 17-03-2016.
 */
public class Map extends JPanel implements ActionListener, FireBulletListener, ChangeListener {
    private static final int TS_MIN = 1;
    private static final int TS_MAX = 25;
    private static final int TS_INIT = 10; //Initial timescale
    private int MapWidth = 300;
    private int MapHeight = 200;
    private Timer timer;
    private boolean isStarted;
    private long Elapsedtime = 0;
    private long HRT = 0; //High Resolution Timer

    public static double FRAMERATE = 16; //Update interval in milliseconds
    public double TIMESCALE = 25;
    public double deltaT = (FRAMERATE / 1000) * TIMESCALE;
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
        initListener(Force1);
        initListener(Force2);
        initSlider();
        initMap();
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

    private void initListener(Force force) {
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
        if(actionEvent.getSource() == Soldier.class){
            int i = 1;
        }

        Elapsedtime = actionEvent.getWhen() - HRT;
        HRT = actionEvent.getWhen();
        updateStates();
        detectCollisions();
        performInstructions();
        repaint();
    }

    private void performInstructions() {
        //Her skal der blot kaldes MySimulation.Run() for hver af sidernes simulation
        for(Step step : Steps)
            step.RunIfCanStart();
    }

    private void updateStates() {
        updateForceStates(Force1);
        updateForceStates(Force2);
    }

    private void updateForceStates(Force force){
        for(Platoon p: force.Platoons){
            for(Group ga: p.Groups){
                for(Soldier s: ga.Soldiers){
                    s.Pos.NewPos(s.Direction, s.Velocity, deltaT);
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
                    if(s.IsEnemyDetected){
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

    @Override
    public void BulletFired(FireBulletEvent event) {
        Bullets.add(event.GetBullet());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int ts = (int)source.getValue();
            deltaT = (FRAMERATE / 1000) * ts;
        }
    }
}
