package com.siemens.spe.spelunky.game.items;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.Position;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by 153627vk on 22.03.2017.
 */
public class LeatherArmor extends Item  {

    public LeatherArmor(Position pos){
        super("LeatherArmor", pos);
        name = "LeatherArmor";
        stats.armor = 0.25;
    }


    @Override
    public void update(Map map) {

    }
}
