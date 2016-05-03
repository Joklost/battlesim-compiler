package com.company.Steps;

import com.company.Objects.SimulationObjects.SimObj;

/**
 * Created by Magnus on 03-05-2016.
 */
public class Interrupt1 extends Step{
    public Interrupt1(SimObj object){
        super(object);
    }
    public void Run(){
        //få det her til at virke
        /*if(!object.IsInCombat() && object.IsGettingShot()){
            object.StopMovement();
            object.DetectEnemies();
        }

        if(object.IsEnemyDetected()){
            object.StopMovement();
            object.Engage();
        }
        */
    }
}
