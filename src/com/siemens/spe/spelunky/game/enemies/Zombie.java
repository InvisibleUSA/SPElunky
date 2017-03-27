package com.siemens.spe.spelunky.game.enemies;

import com.siemens.spe.spelunky.game.player.Player;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.map.TileType;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import com.siemens.spe.spelunky.system.Settings;
import com.siemens.spe.spelunky.system.utility.timer.TimerHandle;
import com.siemens.spe.spelunky.system.utility.timer.TimerManager;
import com.siemens.spe.spelunky.system.utility.timer.TimerType;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by 153627vk on 21.03.2017.
 */
public class Zombie extends Enemy
{
    private int rnd;
    private int dir;

    public Zombie(Position pos)
    {
        super(5, "ZombieA", 1.0, pos, new Position(96,104),23);
        rnd = (int) (Math.random() * 100);
        rnd %= 2;
        dir = rnd;
        if (dir == 0) dir = -1;
    }

    private boolean checkWall(Map map, int x, int y) {
        return map.getTileAt(x, y).getTileType() == TileType.WALL;
    }

    @Override
    protected void move(Map map)
    {
        final int HORIZONTAL = 0;
        final int VERTICAL = 1;
        switch (rnd) {
            case HORIZONTAL:
                moveTo(map, getPosition().x + dir, getPosition().y);
                if (checkWall(map, getPosition().x + dir, getPosition().y))
                    dir *= -1;
                break;
            case VERTICAL:
                moveTo(map, getPosition().x, getPosition().y + dir);
                if (checkWall(map, getPosition().x, getPosition().y + dir))
                    dir *= -1;
                break;
        }
    }
}
