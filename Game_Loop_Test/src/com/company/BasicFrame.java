package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 17-03-2016.
 */
public class BasicFrame extends JFrame {
    public BasicFrame(ArrayList<Vehicle> vehicles){
        InitUI(vehicles);
    }

    private void InitUI(ArrayList<Vehicle> vehicles){
        Map map = new Map(vehicles);
        add(map);
        map.start();
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
