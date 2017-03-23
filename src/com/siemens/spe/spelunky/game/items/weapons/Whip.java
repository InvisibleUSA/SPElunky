package com.siemens.spe.spelunky.game.items.weapons;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.game.enemies.Enemy;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.map.Tile;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

/**
 * Created by 153627vk on 22.03.2017.
 */
public class Whip extends Weapon
{

    public Whip(Position pos)
    {
        super("Whip", pos);
        stats.damage = stats.damage + 1;
        name = "Whip";
    }


    @Override
    public void update(Map map)
    {

    }

    @Override
    public ArrayList<Position> getEnemyPositionInDamageRadius(KeyCode rotation, Position playerPosition, Map map)
    {
        ArrayList<Position> temp = null;
        boolean hitEnemy;
        Tile tile;
        switch (rotation)
        {
            case UP:
                for (int i = -1; i < 3; i++) {
                    temp = getEnemyInTile(map, playerPosition.x + i, playerPosition.y - 1);
                    hitEnemy = (temp != null) && (temp.size() != 0);
                    if (hitEnemy)
                        break;
                    if (i > 0) {
                        temp = getEnemyInTile(map, playerPosition.x - i, playerPosition.y - 1);
                        hitEnemy = (temp != null) && (temp.size() != 0);
                    }
                    if (hitEnemy)
                        break;
                }
                break;
            case DOWN:
                for (int i = 0; i < 3; i++)
                {
                    temp = getEnemyInTile(map, playerPosition.x + i, playerPosition.y + 1);
                    hitEnemy = (temp != null) && (temp.size() != 0);
                    if (hitEnemy)
                        break;
                    if (i > 0) {
                        temp = getEnemyInTile(map, playerPosition.x - i, playerPosition.y + 1);
                        hitEnemy = (temp != null) && (temp.size() != 0);
                    }
                    if (hitEnemy)
                        break;
                }
                break;
            case RIGHT:
                for (int i = 0; i < 3; i++)
                {
                    temp = getEnemyInTile(map, playerPosition.x + 1, playerPosition.y + i);
                    hitEnemy = (temp != null) && (temp.size() != 0);
                    if (hitEnemy)
                        break;
                    if (i > 0) {
                        temp = getEnemyInTile(map, playerPosition.x + 1, playerPosition.y - i);
                        hitEnemy = (temp != null) && (temp.size() != 0);
                    }
                    if (hitEnemy)
                        break;
                }
                break;
            case LEFT:
                for (int i = 0; i < 3; i++) {
                    temp = getEnemyInTile(map, playerPosition.x - 1, playerPosition.y + i);
                    hitEnemy = (temp != null) && (temp.size() != 0);
                    if (hitEnemy)
                        break;
                    if (i > 0) {
                        temp = getEnemyInTile(map, playerPosition.x - 1, playerPosition.y - i);
                        hitEnemy = (temp != null) && (temp.size() != 0);
                    }
                    if (hitEnemy)
                        break;
                }
                break;
            default:
                break;
        }
        return temp;
    }
}