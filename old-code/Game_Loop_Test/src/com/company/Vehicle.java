package com.company;

/**
 * Created by Magnus on 16-03-2016.
 */
public class Vehicle {
    private int posX = 0;
    private int posY = 0;
    private int vectorX = 0;
    private int vectorY = 0;
    private int size = 1;
    private String model;
    private boolean isDead;

    public boolean isDead(){
        return isDead;
    }

    public void kill(){
        setVectorX(0);
        setVectorY(0);
        setModel("x");
        isDead = true;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getVectorX() {
        return vectorX;
    }

    public int getVectorY() {
        return vectorY;
    }

    public int getSize() {
        return size;
    }

    public String getModel() {
        return model;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setVectorX(int vectorX) {
        this.vectorX = vectorX;
    }

    public void setVectorY(int vectorY) {
        this.vectorY = vectorY;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
