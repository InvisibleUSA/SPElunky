package com.siemens.spe.spelunky.system;

import com.siemens.spe.spelunky.map.Map;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by 152544be on 21.03.2017.
 */
public interface GameObject {
    void draw(GraphicsContext gc);

    void update(Map map);

    Position getPosition();
}
