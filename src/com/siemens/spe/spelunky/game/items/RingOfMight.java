package com.siemens.spe.spelunky.game.items;

import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.Position;

/**
 * Created by 153627vk on 23.03.2017.
 */
public class RingOfMight extends Item{
    public RingOfMight(Position pos){
        super("RingOfMight", pos);
        stats.damage =1;
        name = "RingOfMight";
    }

    @Override
    public void update(Map map) {

    }
}
