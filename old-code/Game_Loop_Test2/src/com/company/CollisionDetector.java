package com.company;

import com.company.Objects.Bullet;
import com.company.Objects.SimulationObjects.*;
import com.company.Objects.StaticObjects.Coord;
import com.company.Objects.StaticObjects.Vector;

import java.util.List;

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
                                tryDetectEnemy(s, s2);
                                tryDetectEnemy(s2, s);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void tryDetectEnemy(Soldier s, Soldier enemy){
        if(!s.canStillSeeEnemy()){
            if(!s.IsDead() && !enemy.IsDead()){
                Vector v = Vector.getVectorByPoints(s.getPos(), enemy.getPos());
                if(v.getLength() < s.fov){
                    s.enemyDetected(enemy);
                }
                else{
                    s.isEnemyDetected = false;
                }
            }
        }
    }

    private static void detectProjectileCollisions(Soldier soldier, Bullet bullet){
        if(bullet.owner != soldier.side){
            Vector bulToSol = Vector.getVectorByPoints(bullet.firePos, soldier.getPos());
            Vector projection = bullet.vector.normalize();
            double projLength = bulToSol.dot(bullet.vector.normalize());
            projection.scale(projLength);
            Vector dist = Vector.getVectorByPoints(new Coord(bullet.firePos.x + projection.x, bullet.firePos.y + projection.y), soldier.getPos());
            if(dist.getLength() < soldier.size){
                soldier.kill();
            }
        }
    }
}