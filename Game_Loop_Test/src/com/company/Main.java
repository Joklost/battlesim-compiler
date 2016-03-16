package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Vehicle cent = new Vehicle();
        cent.Velocity = 2;
        cent.VectorX = 1;
        cent.Model = '#';

        Vehicle rcCar = new Vehicle();
        rcCar.PosX = 99;
        rcCar.Velocity = 1;
        rcCar.VectorX = -1;
        rcCar.Model = 'r';

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(cent);
        vehicles.add(rcCar);


        //Game Loop
        while (true) {

            //Først flytes alle objekter
            moveVehicles(vehicles);

            //Derefter kaldes alt collition detection, og events der udspringer af objekternes tilstande
            if(cent.PosX >= 100)
                break;
            if(cent.PosX == rcCar.PosX){
                rcCar.Model = 'x';
                rcCar.Velocity = 0;
            }

            //Render
            render(vehicles);

            //Vent efter den ønskede framerate
            try {
                Thread.sleep(100);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }


    }

    public static void moveVehicles(List<Vehicle> vehicles){
        for(Vehicle vehicle: vehicles)
            vehicle.PosX = vehicle.PosX + vehicle.Velocity * vehicle.VectorX;
    }

    public static void render(List<Vehicle> vehicles){
        char[] map = new char[100];

        for (int i = 0; i < 100; i++) {
            map[i] = '_';
        }

        for(Vehicle vehicle: vehicles)
            map[vehicle.PosX] = vehicle.Model;

        for (int i = 0; i < 100; i++) {
            System.out.print(map[i]);
        }
        System.out.println();
    }
}
