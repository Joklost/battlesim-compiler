package com.company;

import com.company.Objects.SimulationObjects.*;
import com.company.Objects.StaticObjects.Vector;

/**
 * Created by Magnus on 03-05-2016.
 */
public class CollisionDetector {

    public static void visualDetect(Force allies, Force enemies){
        for(Platoon p: allies.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    for(Platoon p2: enemies.Platoons){
                        for(Group g2: p2.Groups){
                            for(Soldier s2: g2.Soldiers){
                                detectEnemy(s, s2);
                                detectEnemy(s2, s);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void detectEnemy(Soldier s, Soldier enemy){
        if(!s.canStillSeeEnemy()){
            if(!s.IsDead() && !enemy.IsDead()){
                Vector v = Vector.getVectorByPoints(s.getPos(), enemy.getPos());
                if(v.getLength() < s.fov){
                    s.enemyDetected(enemy);
                }
            }
        }
    }
}
