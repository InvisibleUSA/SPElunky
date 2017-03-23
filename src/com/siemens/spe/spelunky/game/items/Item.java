package com.siemens.spe.spelunky.game.items;

import com.siemens.spe.spelunky.game.Stats;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import com.siemens.spe.spelunky.system.Settings;
import com.siemens.spe.spelunky.system.TextureManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by 153627vk on 21.03.2017.
 */
public abstract class Item implements GameObject{
    protected Stats stats;
    protected String name;
    private Image texture;
    public Item(String texture, Position pos){
        stats = new Stats();
        this.texture = TextureManager.getTexture(texture);
        stats.position = pos;
    }
    public Stats getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }
    public Image getTexture()
    {
        return texture;
    }

    @Override
    public Position getPosition()
    {
        return this.getStats().position;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(), stats.position.x * Settings.tileDimensionsXY, (stats.position.y * Settings.tileDimensionsXY) - 20, Settings.tileDimensionsXY, Settings.tileDimensionsXY);
    }
}
