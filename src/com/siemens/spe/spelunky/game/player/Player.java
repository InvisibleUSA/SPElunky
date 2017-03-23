package com.siemens.spe.spelunky.game.player;

import com.siemens.spe.spelunky.game.items.Item;
import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.game.enemies.Enemy;
import com.siemens.spe.spelunky.game.items.weapons.Dagger;
import com.siemens.spe.spelunky.game.items.weapons.Weapon;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.map.TileType;
import com.siemens.spe.spelunky.system.*;
import com.siemens.spe.spelunky.system.utility.timer.TimerHandle;
import com.siemens.spe.spelunky.system.utility.timer.TimerManager;
import com.siemens.spe.spelunky.system.utility.timer.TimerType;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 154374ln on 21.03.2017.
 */
public class Player implements GameObject
{
    private Stats stats = new Stats();
    private Image texture;
    private Inventory inventory = new Inventory();
    private boolean dropNext = false;
    private Weapon currentWeapon = new Dagger(new Position(-1,-1));
    private int currentWeaponIndex = -1;
    private MediaPlayer mediaPlayer;
    private Media hit = new Media(new File(System.getProperty("user.dir") + "\\res\\sounds\\hit.mp3").toURI().toString());
    private Media death = new Media(new File(System.getProperty("user.dir") + "\\res\\sounds\\death.mp3").toURI().toString());

    TimerHandle playerAnimationTimer;
    int animationSteps;
    private Position boxPosition;

    public Stats getStats()
    {
        return stats;
    }

    /**
     * Deals @param damage to the Player
     *
     * @param damage
     */
    public void takeDamage(double damage)
    {

        stats.currentHealth -= (damage -(damage * stats.armor));

        if (stats.currentHealth <= 0)
        {
            if (death != null) {
                mediaPlayer = new MediaPlayer(death);
                mediaPlayer.setVolume(10.0);
                System.out.println(mediaPlayer.getVolume());
                mediaPlayer.play();
            }
            Game.gameState = Game.gameState.GAME_OVER_LOST;
            return;
        }
        if (hit != null) {
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(10.0);
            System.out.println(mediaPlayer.getVolume());
            mediaPlayer.play();
        }
    }
    public int getCurrentWeaponIndex()
    {
        return currentWeaponIndex;
    }

    /**
     * Deals damage to Enemy
     */
    public boolean dealDamage(Map map, KeyCode playerRotation, Position pos)
    {
        ArrayList<Position> list = currentWeapon.getEnemyPositionInDamageRadius(playerRotation,pos,map);
        if (list == null) return false;
        for (Position position: list)
        {
            for (GameObject enemy: map.getTileAt(position).getCurrent())
            {
                if (enemy instanceof Enemy) {
                    ((Enemy)enemy).takeDamage(currentWeapon.getStats().damage + stats.damage, map);
                    return true;
                }
            }
        }
        return false;

    }

    public void move(Map map)
    {
        Position pos = this.getStats().position;
        int x = 0, y = 0;
        KeyCode keyCode = null;
        switch (Window.keyInput.getCode())
        {
            case UP:
                keyCode = KeyCode.UP;
                if (map.getTileAt(pos.x, (pos.y - 1)).getTileType() == TileType.WALL)
                {
                    break;
                }
                if (dealDamage(map, keyCode, this.getPosition())) return;
                y -= 1;
                break;
            case DOWN:
                keyCode = KeyCode.DOWN;
                if (map.getTileAt(pos.x, (pos.y + 1)).getTileType() == TileType.WALL)
                {
                    break;
                }
                y += 1;
                if (dealDamage(map, keyCode, this.getPosition())) return;
                break;
            case RIGHT:
                keyCode = KeyCode.RIGHT;
                if (map.getTileAt((pos.x + 1), pos.y).getTileType() == TileType.WALL)
                {
                    break;
                }
                if (dealDamage(map, keyCode, this.getPosition())) return;
                x += 1;
                break;
            case LEFT:
                keyCode = KeyCode.LEFT;
                if (map.getTileAt((pos.x - 1), pos.y).getTileType() == TileType.WALL)
                {
                    break;
                }
                if (dealDamage(map, keyCode, this.getPosition())) return;
                x -= 1;
                break;
            case Q:
                dropNext = !dropNext;
                break;
            case DIGIT1:
                if (dropNext)
                {
                    inventory.dropItem(0, map, this);
                    dropNext = false;
                }
                else
                {
                    useItem(0);
                }
                break;
            case DIGIT2:
                if (dropNext)
                {
                    inventory.dropItem(1, map, this);
                    dropNext = false;
                }
                else
                {
                    useItem(1);
                }
                break;
            case DIGIT3:
                if (dropNext)
                {
                    inventory.dropItem(2, map, this);
                    dropNext = false;
                }
                else
                {
                    useItem(2);
                }
                break;
            case DIGIT4:
                if (dropNext)
                {
                    inventory.dropItem(3, map, this);
                    dropNext = false;
                }
                else
                {
                    useItem(3);
                }
                break;
            case DIGIT5:
                if (dropNext)
                {
                    inventory.dropItem(4, map, this);
                    dropNext = false;
                }
                else
                {
                    useItem(4);
                }
                break;
            case SPACE:
                break;
            default:
                return;
        }
        /*
        for (GameObject element : map.getTileAt(new Position(this.getPosition().x + x, this.getPosition().y + y)).getCurrent())
        {
            if (element instanceof Enemy && keyCode != null)
            {
                dealDamage(map, keyCode, this.getPosition());
                return;
            }
        }
        */
        ArrayList<GameObject> tmpAl = map.getTileAt(new Position(this.getPosition().x + x, this.getPosition().y + y)).getCurrent();
        for (int i = 0; i < tmpAl.size(); i++)
        {
            if (tmpAl.get(i) instanceof Item)
            {
                pickupItem((Item) tmpAl.get(i), map);
            }
        }
        if (map.getTileAt(getPosition().x + x, getPosition().y + y).getTileType() == TileType.EXIT_STAIRS)
            Game.gameState = GameState.GAME_OVER_WON;
        this.getStats().position.x += x;
        this.getStats().position.y += y;
    }

    private void pickupItem(Item item, Map map)
    {
        if (item instanceof Weapon)
        {
            currentWeapon = (Weapon) item;
        }
        inventory.addToInv(item, map, item.getPosition());
    }

    private void useItem(int index)
    {
        if (inventory.getItemAt(index) instanceof Weapon)
        {
            currentWeapon = (Weapon) inventory.getItemAt(index);
            currentWeaponIndex = index;
            return;
        }
        if (inventory.getItemAt(index) != null)
        {
            Stats tmpStats = inventory.useItemAt(index).getStats();
            if ((tmpStats.currentHealth + this.getStats().currentHealth) > this.getStats().maxHealth){this.getStats().currentHealth = this.getStats().maxHealth;}else{this.getStats().currentHealth += tmpStats.currentHealth;}
            this.getStats().maxHealth += tmpStats.maxHealth;
            this.getStats().damage += tmpStats.damage;
        }
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        gc.drawImage(texture, animationSteps*boxPosition.y, 0, boxPosition.x, boxPosition.y, stats.position.x * Settings.tileDimensionsXY, (stats.position.y * Settings.tileDimensionsXY) - 20, Settings.tileDimensionsXY, Settings.tileDimensionsXY);
        inventory.draw(gc);
    }

    @Override
    public void update(Map map)
    {
        move(map);
        if (playerAnimationTimer.ready()) {
            if (animationSteps < 3) {
                ++animationSteps;
            } else {
                animationSteps = 0;
            }
        }
    }

    @Override
    public Position getPosition()
    {
        return this.getStats().position;
    }

    public Player(Position pos)
    {
        texture = TextureManager.getTexture("PlayerA");
        this.getStats().currentHealth = 5;
        this.getStats().maxHealth = 5;
        this.getStats().armor = 0;
        stats.position = pos;
        playerAnimationTimer = TimerManager.requestTimerHandle(TimerType.AUTO_TICK_TIMER, -1);
        boxPosition = new Position(384/4,100);
    }
    public Inventory getPlayerInventory()
    {
        return inventory;
    }
}
