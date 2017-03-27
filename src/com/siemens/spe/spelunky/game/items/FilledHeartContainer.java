package com.siemens.spe.spelunky.game.items;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.Position;

/**
 * Created by 153627vk on 22.03.2017.
 */
public class FilledHeartContainer extends Item  {

    public FilledHeartContainer(Position pos) {
        super("FilledHeartContainer", pos);
        stats.currentHealth = 1;
        name = "FilledHeartContainer";

    }

    @Override
    public void update(Map map) {

    }
}
