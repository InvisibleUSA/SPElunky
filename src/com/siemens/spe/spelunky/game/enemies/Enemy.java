package com.siemens.spe.spelunky.game.enemies;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.game.player.Player;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.map.TileType;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import com.siemens.spe.spelunky.system.Settings;
import com.siemens.spe.spelunky.system.TextureManager;
import com.siemens.spe.spelunky.system.utility.timer.TimerHandle;
import com.siemens.spe.spelunky.system.utility.timer.TimerManager;
import com.siemens.spe.spelunky.system.utility.timer.TimerType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by 154374ln on 21.03.2017.
 */
public abstract class Enemy implements GameObject {
    private TimerHandle moveTimer = TimerManager.requestTimerHandle(TimerType.AUTO_TICK_TIMER, Settings.EnemyWaitTicK);
    private TimerHandle animationTimer = TimerManager.requestTimerHandle(TimerType.AUTO_TICK_TIMER, Settings.AnimationTickTimer);
    private int currentAnimationSteps;
    private int animationSteps;
    private Stats stats;
    private javafx.scene.image.Image texture;
    private Position boxPosition;
    protected boolean hasMoved;

    protected abstract void move(Map map);

    public Stats getStats() {
        return stats;
    }

    protected void moveTo(Map map, int x, int y) {
        if (map.getPlayer().getPosition().equals(x, y)) {
            dealDamage(map.getPlayer());
            return;
        }
        if ((map.getTileAt(x, y).getTileType() == TileType.WALL) || (map.getTileAt(x,y).getCurrent().size() != 0)){
            return;
        }
        Position src = stats.position.clone();
        stats.position.x = x;
        stats.position.y = y;
        map.moveEntity(src, this);
        hasMoved = true;
    }

    /**
     * Deals damage to the Player
     */
    public void dealDamage(Player player) {
        player.takeDamage(this.getStats().getDamage());
    }

    /**
     * Deals damage to the Enemy
     */
    public void takeDamage(double damage, Map map) {
        stats.currentHealth -= damage;
        if (stats.currentHealth <= 0) {
            map.remove(this.getPosition(), this);
        }
    }

    public void update(Map map) {
        hasMoved = false;
        if (moveTimer.ready()) {
            move(map);
        }
        if (animationTimer.ready()) {
            if (currentAnimationSteps < animationSteps) {
                ++currentAnimationSteps;
            } else {
                currentAnimationSteps = 0;
            }
        }
    }

    public Enemy(double health, String texture, double damage, Position pos, Position boxPosition, int animationSteps) {
        stats = new Stats();
        stats.currentHealth = health;
        stats.maxHealth = health;
        stats.armor = 0;
        stats.position = pos;
        this.texture = TextureManager.getTexture(texture);
        this.getStats().setDamage(damage);
        this.boxPosition = boxPosition;
        this.animationSteps = animationSteps;
        hasMoved = false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(texture, currentAnimationSteps * boxPosition.x, 0, boxPosition.x, boxPosition.y, stats.position.x * Settings.tileDimensionsXY, stats.position.y * Settings.tileDimensionsXY, Settings.tileDimensionsXY, Settings.tileDimensionsXY);

        if (stats.currentHealth / stats.maxHealth < 1) {
            gc.setFill(Color.LIGHTGRAY);
            gc.fillRect(stats.position.x * Settings.tileDimensionsXY, stats.position.y * Settings.tileDimensionsXY, Settings.tileDimensionsXY, 4);
            gc.setFill(Color.DARKRED);
            gc.fillRect(stats.position.x * Settings.tileDimensionsXY, stats.position.y * Settings.tileDimensionsXY, stats.currentHealth / stats.maxHealth * Settings.tileDimensionsXY, 4);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(stats.position.x * Settings.tileDimensionsXY, stats.position.y * Settings.tileDimensionsXY, Settings.tileDimensionsXY, 4);
        }
    }

    @Override
    public Position getPosition() {
        return stats.position;
    }

}
