package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static int MAX_X = 100;
    public static int MAX_Y = 5;

    public static void main(String[] args) {

        Vehicle cent = new Vehicle();
        cent.PosX = 1;
        cent.Velocity = 1;
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
            if(cent.PosX == 98){
                cent.VectorX = 0;
                cent.VectorY = 1;
            }

            if(cent.PosX >= MAX_X || cent.PosY >= MAX_Y)
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
        for(Vehicle vehicle: vehicles){
            vehicle.PosX = vehicle.PosX + vehicle.Velocity * vehicle.VectorX;
            vehicle.PosY = vehicle.PosY + vehicle.Velocity * vehicle.VectorY;
        }

    }

    public static void render(List<Vehicle> vehicles){
        char[][] map = new char[MAX_X][MAX_Y];

        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                map[i][j] = '_';
            }
        }

        for(Vehicle vehicle: vehicles)
            map[vehicle.PosX][vehicle.PosY] = vehicle.Model;

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
    }
}
