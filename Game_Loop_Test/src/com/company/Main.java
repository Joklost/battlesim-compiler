package com.company;

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

    public static void moveVehiclesPos(List<Vehicle> vehicles){
        for(Vehicle vehicle: vehicles){
            vehicle.setPosX(vehicle.getPosX() + vehicle.getVelocity() * vehicle.getVectorX()) ;
            vehicle.setPosY(vehicle.getPosY() + vehicle.getVelocity() * vehicle.getVectorY());
        }

    }

    public static void runSimulation() {


        Vehicle cent = new Vehicle();
        cent.setPosX(200);
        cent.setPosY(100);
        cent.setVectorX(-1);
        cent.setVectorY(0);
        cent.setModel("Cent");
        cent.setSize(30);

        Vehicle rcCar = new Vehicle();
        rcCar.setPosX(100);
        rcCar.setPosY(100);
        rcCar.setVectorX(1);
        rcCar.setVectorY(0);
        rcCar.setSize(10);
        rcCar.setModel("rc");

        Vehicle batMobileCar = new Vehicle();
        batMobileCar.setPosX(50);
        batMobileCar.setPosY(50);
        batMobileCar.setVectorX(-5);
        batMobileCar.setVectorY(2);
        batMobileCar.setSize(20);
        batMobileCar.setModel("bat");

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(cent);
        vehicles.add(rcCar);
        vehicles.add(batMobileCar);
        BasicFrame ex = new BasicFrame(vehicles);
        ex.setVisible(true);

        /*try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }*/


        //Game Loop
        /*while (true) {

            //Først flytes alle objekter
            moveVehiclesPos(vehicles);

            //Derefter kaldes alt collition detection, og events der udspringer af objekternes tilstande
            if(cent.getPosX() == 98){
                cent.setVectorX(0);
                cent.setVectorY(1);
            }

            if(cent.getPosX() >= MAX_X || cent.getPosY() >= MAX_Y)
                break;
            if(cent.getPosX() == rcCar.getPosX()){
                rcCar.setModel("x");
                rcCar.setVelocity(0);
            }

            //Render
            //render(vehicles);
            ex.repaint();

            //Vent efter den ønskede framerate
            try {
                Thread.sleep(100);                 //1000 milliseconds is one second.
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }*/
    }

    /*public static void render(List<Vehicle> vehicles){
        char[][] map = new char[MAX_X][MAX_Y];

        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                map[i][j] = '_';
            }
        }

        for(Vehicle vehicle: vehicles)
            map[vehicle.getPosX()][vehicle.getPosY()] = vehicle.getModel();

        for (int i = 0; i < MAX_Y; i++) {
            for (int j = 0; j < MAX_X; j++) {
                System.out.print(map[j][i]);
            }
            System.out.println();
        }
        System.out.println();

    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }*/
}
