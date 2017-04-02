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
    private TimerHandle moveTimer = TimerManager.requestTimerHandle(TimerType.AUTO_TICK_TIMER, Settings.EnemyWaitTick);
    private TimerHandle animationTimer = TimerManager.requestTimerHandle(TimerType.AUTO_COUNTDOWN_TIMER, Settings.AnimationTimer);
    private int currentAnimationSteps;
    private int animationSteps;
    private Stats stats;
    private javafx.scene.image.Image texture;
    private Position rectAnimationFrame;
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
        //TODO check if the function works previous: (map.getTileAt(x, y).getTileType() == TileType.WALL) || (map.getTileAt(x,y).getCurrent().size() != 0)
        if (map.getTileAt(x, y).blocksEnemies()){
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
    }

    /**
     * This constructor should be called as super-constructor when inheriting from Enemy.
     * All parameters except position should be absolute in child class.
     *
     * If you want to have an animation for the enemy, simply use a single image file where all animation frames are in a single line (and have the same width and height).
     * @param health Amount of health the enemy has
     * @param texture name of picture used when drawing the enemy
     * @param damage damage the enemy deals when it hits the player
     * @param pos the position it starts at
     * @param rectAnimationFrame height and width of a single frame, whole picture if null
     * @param animationSteps number of frames the animation has
     */
    public Enemy(double health, String texture, double damage, Position pos, Position rectAnimationFrame, int animationSteps) {
        stats = new Stats();
        stats.currentHealth = health;
        stats.maxHealth = health;
        stats.armor = 0;
        stats.position = pos;
        this.texture = TextureManager.getTexture(texture);
        this.getStats().setDamage(damage);
        this.rectAnimationFrame = rectAnimationFrame;
        this.animationSteps = animationSteps;
        hasMoved = false;

        if (this.rectAnimationFrame == null)
            this.rectAnimationFrame = new Position((int) this.texture.getWidth(), (int) this.texture.getHeight());
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (animationTimer.ready()) {
            if (currentAnimationSteps < animationSteps) {
                ++currentAnimationSteps;
            } else {
                currentAnimationSteps = 0;
            }
        }

        gc.drawImage(texture, currentAnimationSteps * rectAnimationFrame.x, 0, rectAnimationFrame.x, rectAnimationFrame.y, stats.position.x * Settings.tileDimensionsXY, stats.position.y * Settings.tileDimensionsXY, Settings.tileDimensionsXY, Settings.tileDimensionsXY);

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
