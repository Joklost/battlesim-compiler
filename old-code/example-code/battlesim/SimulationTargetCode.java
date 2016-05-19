package com.company;

import static com.BattleSim.Declarations;
import...

import java.util.ArrayList;

/**
 * Created by Magnus on 04-05-2016.
 */
public class protectTheGeneral {

    public protectTheGeneral(){
        MoveStep s1 = new MoveStep(allies, new Coord(300,250));
        MoveStep s2 = new MoveStep(aGroup1, new Coord(600, 300));
        MoveStep s3 = new MoveStep(aGroup2, new Coord(600, 200));
        MoveDetectStep s4 = new MoveDetectStep(aGroup1, new Coord(600,250));
        MoveDetectStep s5 = new MoveDetectStep(aGroup1, new Coord(600,250));
        MoveStep s6 = new MoveStep(general, new Coord(600,250));
        MoveStep s7 = new MoveStep(general, new Coord(Base));

    }
    public void Run(){
        Interrupts();
        s1.RunIfCanStart();
        s2.RunIfCanStart();
        s3.RunIfCanStart();
        if(s4.IsDone && s5.IsDone){
            if(!aGroup.IsDead() && !aGroup2.IsDead()){
                s6.RunIfCanStart();
            }
            else{
                s7.RunIfCanStart();
            }
        }
    }

    public void Interrupts(){
        if(!allies.IsInCombat && allies.IsGettingShot()){
            allies.StopMovement();
            allies.DetectEnemies();
        }
        if(allies.IsEnemyDetected()){
            allies.StopMovement();
            allies.Engage();
        }
    }
}
