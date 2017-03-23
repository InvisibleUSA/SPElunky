package com.siemens.spe.spelunky.ui;

import com.siemens.spe.spelunky.game.items.Item;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import com.siemens.spe.spelunky.system.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by 152544be on 22.03.2017.
 */
public class UIInventoryBox implements GameObject {
    Position screenSize, invRoot, invSize, itemOffset;
    int itemSize, itemDistance, currentItem;
    Image invBackground;
    Image[] invPictures;

    public UIInventoryBox(Position screenSize, Position invSize, Position itemOffset, int itemSize, int itemDistance) {
        this.screenSize = screenSize;
        this.itemOffset = itemOffset;
        this.itemSize = itemSize;
        this.itemDistance = itemDistance;

        this.invRoot = new Position(this.screenSize.x - invSize.x, 0);
        this.invSize = invSize;
        invPictures = new Image[5];
    }

    public void setBackground(Image img) {
        invBackground = img;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(invBackground, invRoot.x, invRoot.y, invSize.x, invSize.y);
        for (int i = 0; i < 5; ++i) {
            if (invPictures[i] != null) {
                gc.drawImage(invPictures[i], invRoot.x + itemOffset.x, invRoot.y + itemOffset.y + i * itemDistance, itemSize, itemSize);
            }
            if(i == currentItem)
            {
                gc.setStroke(Color.RED);
                gc.strokeRect(invRoot.x + itemOffset.x, invRoot.y + itemOffset.y + i * itemDistance, itemSize, itemSize);
            }
        }
    }

    @Override
    public void update(Map map) {
        for(int i = 0; i < 5; ++i)
        {
            Item it = map.getPlayer().getPlayerInventory().getItemAt(i);
            if(it != null)
            {
                invPictures[i] = it.getTexture();
            }
            else
            {
                invPictures[i] = null;
            }
        }
        currentItem = map.getPlayer().getCurrentWeaponIndex();
    }

    @Override
    public Position getPosition() {
        return invRoot;
    }
}
