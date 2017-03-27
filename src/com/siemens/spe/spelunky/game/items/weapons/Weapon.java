package com.siemens.spe.spelunky.game.items.weapons;

import com.siemens.spe.spelunky.game.enemies.Enemy;
import com.siemens.spe.spelunky.game.items.Item;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

/**
 * Created by 154374ln on 22.03.2017.
 */
public abstract class Weapon extends Item
{
    public abstract ArrayList<Position> getEnemyPositionInDamageRadius(KeyCode rotation, Position playerPosition, Map map);

    public Weapon(String texture, Position pos){
        super(texture, pos);
    }

    protected ArrayList<Position> getEnemyInTile(Map map, int x, int y){
        ArrayList<Position> temp = new ArrayList<>();
        if (map.getTileAt(x, y) == null) return null;
        for (GameObject object : map.getTileAt(x, y).getCurrent())
        {
            if (object instanceof Enemy)
            {
                temp.add(object.getPosition());
            }
        }
        return temp;
    }
}
