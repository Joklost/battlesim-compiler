package com.company;

import com.company.Objects.SimulationObjects.*;
import com.company.Objects.StaticObjects.Vector;

/**
 * Created by Magnus on 03-05-2016.
 */
public class StandardInterrupts {

    public static void Check(Force allies, Force enemies){
        for(Platoon p: allies.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    for(Platoon p2: enemies.Platoons){
                        for(Group g2: p2.Groups){
                            for(Soldier s2: g2.Soldiers){
                                Vector v = Vector.GetVectorByPoints(s.GetPos(), s2.GetPos());
                                if(v.GetLength() < s.Fov){
                                    s.EnemyDetected(s2);
                                    s2.EnemyDetected(s);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
