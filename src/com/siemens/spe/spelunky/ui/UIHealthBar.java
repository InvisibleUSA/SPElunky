package com.siemens.spe.spelunky.ui;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by 152544be on 22.03.2017.
 */
public class UIHealthBar implements GameObject {
    Image[] healthBar;

    double percent;
    double oldHealth;
    Position healthBarRoot, healthBarSize;

    public UIHealthBar(Position healthBarRoot, Position healthBarSize) {
        this.healthBarRoot = healthBarRoot;
        this.healthBarSize = healthBarSize;
        healthBar = new Image[3];
        percent = 1;
    }

    public void setHealthBarLayer(Image background, Image bar, Image foreground) {
        healthBar[0] = background;
        healthBar[1] = bar;
        healthBar[2] = foreground;
    }

    public void setHealthPercentage(double percent) {
        this.percent = percent;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(healthBar[0], 0, 0, healthBar[0].getWidth(), healthBar[0].getHeight(), healthBarRoot.x, healthBarRoot.y, healthBarSize.x, healthBarSize.y);
        gc.drawImage(healthBar[1], 0, 0, healthBar[1].getWidth()*percent, healthBar[1].getHeight(), healthBarRoot.x, healthBarRoot.y, healthBarSize.x*percent, healthBarSize.y);
        gc.drawImage(healthBar[2], 0, 0, healthBar[2].getWidth(), healthBar[2].getHeight(), healthBarRoot.x, healthBarRoot.y, healthBarSize.x, healthBarSize.y);

    }

    @Override
    public void update(Map map) {
        Stats tmpStats = map.getPlayer().getStats();
        if(tmpStats.maxHealth > oldHealth)
        {
           if(oldHealth == 0)
           {
               oldHealth = tmpStats.maxHealth;
           }
           healthBarSize.x *= tmpStats.maxHealth/oldHealth;
            oldHealth = tmpStats.maxHealth;
        }
        setHealthPercentage(tmpStats.currentHealth / tmpStats.maxHealth);
    }

    @Override
    public Position getPosition() {
        return new Position(-1, -1);
    }

}
