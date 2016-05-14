package com.BattleSim;

/**
 * Created by Magnus on 03-05-2016.
 */
public class CollisionDetector {

    public static void VisualDetect(Force allies, Force enemies){
        for(Platoon p: allies.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    for(Platoon p2: enemies.Platoons){
                        for(Group g2: p2.Groups){
                            for(Soldier s2: g2.Soldiers){
                                DetectEnemy(s, s2);
                                DetectEnemy(s2, s);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void DetectEnemy(Soldier s, Soldier enemy){
        if(!s.CanStillSeeEnemy()){
            if(!s.IsDead() && !enemy.IsDead()){
                Vector v = Vector.GetVectorByPoints(s.GetPos(), enemy.GetPos());
                if(v.GetLength() < s.Fov){
                    s.EnemyDetected(enemy);
                }
                else{
                    s.IsEnemyDetected = false;
                }
            }
        }
    }
}
