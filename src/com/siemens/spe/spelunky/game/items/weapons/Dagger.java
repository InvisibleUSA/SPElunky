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
public class Dagger extends Weapon
{
    public Dagger(Position pos)
    {
        super("Dagger", pos);
        stats.damage = 1;
        name = "Dagger";

    }

    @Override
    public void update(Map map)
    {

    }

    @Override
    public ArrayList<Position> getEnemyPositionInDamageRadius(KeyCode rotation, Position playerPosition, Map map)
    {
        switch (rotation)
        {
            case UP:
                return getEnemyInTile(map, playerPosition.x, playerPosition.y - 1);
            case DOWN:
                return getEnemyInTile(map, playerPosition.x, playerPosition.y + 1);
            case RIGHT:
                return getEnemyInTile(map, playerPosition.x + 1, playerPosition.y);
            case LEFT:
                return getEnemyInTile(map, playerPosition.x - 1, playerPosition.y);
            default:
                return null;
        }
    }
}
