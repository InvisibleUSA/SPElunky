package com.siemens.spe.spelunky.game.items;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.Position;

/**
 * Created by 153627vk on 22.03.2017.
 */
public class EmptyHeartContainer extends Item {

    public EmptyHeartContainer(Position pos){
        super("EmptyHeartContainer", pos);
        stats.maxHealth =1;
        name = "EmptyHeartContainer";

    }

    @Override
    public void update(Map map) {

    }
}
