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
public class BlueSlime extends Enemy
{
    private int cnt = 1;

    public BlueSlime(Position pos)
    {
        super(2, "BlueSlimeA", 1.0, pos, new Position(104,108),3);
    }

    @Override
    protected void move(Map map)
    {
        moveTo(map, getPosition().x + cnt, getPosition().y);
        if (!map.getPlayer().getPosition().equals(getPosition().x + cnt, getPosition().y))
            cnt *= -1;
    }
}
