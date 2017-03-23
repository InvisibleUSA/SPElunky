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
public class OrangeSlime extends Enemy
{
    private int cnt = 0;

    public OrangeSlime(Position pos)
    {
        super(1, "OrangeSlimeA", 0.5, pos, new Position(104,108),3);
    }

    @Override
    protected void move(Map map)
    {
        int x = this.getStats().position.x;
        int y = this.getStats().position.y;
        switch (cnt % 4)
        {
            case 0:
                moveTo(map, ++x, y);
                break;
            case 1:
                moveTo(map, x, --y);
                break;
            case 2:
                moveTo(map, --x, y);
                break;
            case 3:
                moveTo(map, x, ++y);
                break;
            default:
                break;
        }
        if (hasMoved)
            cnt++;
    }
}
