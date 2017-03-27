package com.siemens.spe.spelunky.game.enemies;

import com.siemens.spe.spelunky.game.player.Player;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.map.TileType;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;

/**
 * Created by 154374ln on 21.03.2017.
 */
public class Bat extends Enemy {


    public Bat(Position pos) {
        super(1, "BatA", 1.0, pos, new Position(96,96),3);
    }

    @Override
    protected void move(Map map) {
        int rnd = (int) (Math.random() * 100);
        rnd %= 4;
        int x = this.getStats().position.x;
        int y = this.getStats().position.y;
        switch (rnd) {
            case 0:
                moveTo(map, x + 1, y);
                break;
            case 1:
                moveTo(map, x - 1, y);
                break;
            case 2:
                moveTo(map, x, y + 1);
                break;
            case 3:
                moveTo(map, x, y - 1);
                break;
            default:
                break;
        }
    }

    @Override
    public Position getPosition() {
        return this.getStats().position;
    }
}
