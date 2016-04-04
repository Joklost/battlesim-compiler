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
    }
}
