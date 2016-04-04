package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Magnus on 17-03-2016.
 */
public class Map extends JPanel implements ActionListener {

    private final int MapWidth = 300;
    private final int MapHeight = 200;

    private Timer timer;
    private boolean isStarted;
    private ArrayList<Vehicle> vehicles;

    public Map(ArrayList<Vehicle> vehicles){
        this.vehicles = vehicles;
        initMap();
    }

    private void initMap() {
        setFocusable(true);
        timer = new Timer(50, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(isWinnerFound()){
            return;
        }else{
            moveVehicles();
            detectCollisions();
        }

    }

    private void moveVehicles() {
        for(Vehicle vehicle: vehicles){
            vehicle.setPosX(vehicle.getPosX() + vehicle.getVectorX());
            vehicle.setPosY(vehicle.getPosY() + vehicle.getVectorY());
        }
        repaint();
    }

    private void detectCollisions(){

        for(Vehicle vehicle: vehicles){
            if(vehicle.getPosX() - vehicle.getSize() / 2 == 0 || vehicle.getPosX() + vehicle.getSize() / 2 == MapWidth){
                vehicle.setVectorX(vehicle.getVectorX() * -1);
            }
            if(vehicle.getPosY() == 0 - vehicle.getSize() / 2|| vehicle.getPosY() + vehicle.getSize() / 2 == MapWidth){
                vehicle.setVectorY(vehicle.getVectorY() * -1);
            }
            //vehicle boundaries
            int vehicleL = vehicle.getPosX() - vehicle.getSize() / 2;
            int vehicleR = vehicle.getPosX() + vehicle.getSize() / 2;
            int vehicleU = vehicle.getPosY() + vehicle.getSize() / 2;
            int vehicleD = vehicle.getPosY() - vehicle.getSize() / 2;

            //is nestedVehicles middle inside vehicle
            for(Vehicle nestedVehicle: vehicles){
                if(vehicle.getModel() != nestedVehicle.getModel() && vehicle.getSize() > nestedVehicle.getSize() && !nestedVehicle.isDead()){
                    if(nestedVehicle.getPosX() > vehicleL && nestedVehicle.getPosX() < vehicleR
                            && nestedVehicle.getPosY() < vehicleU && nestedVehicle.getPosY() > vehicleD){
                        nestedVehicle.kill();
                    }
                }
            }
        }


    }

    private boolean isWinnerFound(){
        int alive = 0;
        for(Vehicle vehicle: vehicles){
            if(!vehicle.isDead())
                alive++;
        }
        return alive == 1;
    }

    public void start(){

        timer.start();
    }

    private void doDrawing(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        for (Vehicle vehicle : this.vehicles) {
            g2d.drawString(vehicle.getModel(), vehicle.getPosX(), vehicle.getPosY());
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
}
