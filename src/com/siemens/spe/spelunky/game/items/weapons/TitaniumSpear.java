package com.siemens.spe.spelunky.game.items.weapons;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.game.enemies.Enemy;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

/**
 * Created by 153627vk on 22.03.2017.
 */
public class TitaniumSpear extends Weapon {

    public TitaniumSpear(Position pos){
        super("TitaniumSpear", pos);
        stats.damage =2;
        name = "TitaniumSpear";
    }


    @Override
    public void update(Map map) {

    }

    @Override
    public ArrayList<Position> getEnemyPositionInDamageRadius(KeyCode rotation, Position playerPosition, Map map) {
        ArrayList<Position> temp = new ArrayList<>();
        boolean hitEnemy;
        switch (rotation)
        {
            case UP:
                hitEnemy = false;
                for (int i = 1; i < 3; i++)
                {
                    for (GameObject object: map.getTileAt(playerPosition.x, playerPosition.y-i).getCurrent())
                    {
                        if (object instanceof Enemy)
                        {
                            temp.add(object.getPosition());
                            hitEnemy = true;
                            break;
                        }
                    }
                    if (hitEnemy)
                    {
                        break;
                    }
                }
                break;
            case DOWN:
                hitEnemy = false;
                for (int i = 1; i < 3; i++)
                {
                    for (GameObject object: map.getTileAt(playerPosition.x, playerPosition.y+i).getCurrent())
                    {
                        if (object instanceof Enemy)
                        {
                            temp.add(object.getPosition());
                            hitEnemy = true;
                            break;
                        }
                    }
                    if (hitEnemy)
                    {
                        break;
                    }
                }
                break;
            case RIGHT:
                hitEnemy = false;
                for (int i = 1; i < 3; i++)
                {
                    for (GameObject object: map.getTileAt(playerPosition.x+i, playerPosition.y).getCurrent())
                    {
                        if (object instanceof Enemy)
                        {
                            temp.add(object.getPosition());
                            hitEnemy = true;
                            break;
                        }
                    }
                    if (hitEnemy)
                    {
                        break;
                    }
                }
                break;
            case LEFT:
                hitEnemy = false;
                for (int i = 1; i < 3; i++)
                {
                    ArrayList<GameObject> list = map.getTileAt(playerPosition.x-i, playerPosition.y).getCurrent();
                    System.out.println(list.size());
                    for (GameObject object: list)
                    {
                        if (object instanceof Enemy)
                        {
                            temp.add(object.getPosition());
                            hitEnemy = true;
                            break;
                        }
                    }
                    if (hitEnemy)
                    {
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return temp;
    }
}
