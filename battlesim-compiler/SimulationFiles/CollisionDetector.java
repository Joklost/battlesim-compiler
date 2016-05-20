package com.BattleSim;

import java.util.List;
import java.util.Random;
/**
 * Created by Magnus on 03-05-2016.
 */
public class CollisionDetector {

    public static void detectAllCollisions(Force allies, Force enemies, List<Bullet> bullets){
        for(Platoon p: allies.Platoons){
            for(Group g: p.Groups){
                for(Soldier s: g.Soldiers){
                    for(Platoon p2: enemies.Platoons){
                        for(Group g2: p2.Groups){
                            for(Soldier s2: g2.Soldiers){
                                for(Bullet b: bullets){
                                    detectProjectileCollisions(s, b);
                                    detectProjectileCollisions(s2, b);
                                }
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
                if(v.GetLength() < s.fov){
                    Random rand = new Random();
                    double range = s.minRange + (s.fov - s.minRange) * rand.nextDouble(); // return a double between s.minRange and s.fov
                    if(v.GetLength() < range){
                        if(s.cl_detectRate >= s.detectRate){
                            s.enemyDetected(enemy);
                            s.cl_detectRate = 0;
                        }
                    }
                }
                else{
                    s.IsEnemyDetected = false;
                }
            }
        }
    }

    private static void detectProjectileCollisions(Soldier soldier, Bullet bullet){
        if(bullet.Owner != soldier.Side){
            Vector bulToSol = Vector.GetVectorByPoints(bullet.FirePos, soldier.GetPos());
            Vector projection = bullet.Vec.Normalize();
            double projLength = bulToSol.dot(bullet.Vec.Normalize());
            projection.Scale(projLength);
            Vector dist = Vector.GetVectorByPoints(new Coord(bullet.FirePos.X + projection.X, bullet.FirePos.Y + projection.Y), soldier.GetPos());
            if(dist.GetLength() < soldier.Size){
                soldier.Kill();
            }
        }
    }
}
