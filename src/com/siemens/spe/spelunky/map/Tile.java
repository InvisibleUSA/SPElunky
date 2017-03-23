package com.siemens.spe.spelunky.map;

import com.siemens.spe.spelunky.system.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by 152544be on 21.03.2017.
 */
public class Tile {
    private ArrayList<GameObject> current;
    private TileType tileType;
    private static Image imageWall;
    private static Image imageGround;
    private static Image imageStairs;
    private Position position;

    public Tile(Position position) {
        this.position = position;
        current = new ArrayList<GameObject>();
        imageWall = TextureManager.getTexture("Wall");
        imageGround = TextureManager.getTexture("Ground");
        imageStairs = TextureManager.getTexture("Exit");
    }

    public Tile(int x, int y) {
        this(new Position(x,y));
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return this.tileType;
    }

    public void addGameObject(GameObject obj) {
        current.add(obj);

    }

    public ArrayList<GameObject> getCurrent() {
        return current;
    }

    public void draw(GraphicsContext gc) {
        final double scale = 0.6;

        if(tileType == TileType.GROUND || tileType == TileType.ENTITY)
        {
            gc.drawImage(imageGround, 0, 0, imageGround.getWidth(), imageGround.getHeight(), position.x * Settings.tileDimensionsXY, position.y * Settings.tileDimensionsXY, Settings.tileDimensionsXY, Settings.tileDimensionsXY);
        }
        if(tileType == TileType.WALL)
        {
            gc.drawImage(imageWall, 0, 0, imageWall.getWidth(), imageWall.getHeight(), position.x * Settings.tileDimensionsXY, (position.y - scale) * Settings.tileDimensionsXY, Settings.tileDimensionsXY, Settings.tileDimensionsXY * (1 + scale));

        }
        if(tileType == TileType.ENTITY)
        {
            current.forEach((GameObject go) -> go.draw(gc));
        }
        if (tileType == TileType.EXIT_STAIRS)
        {
            gc.drawImage(imageStairs, 0, 0, imageGround.getWidth(), imageGround.getHeight(), position.x * Settings.tileDimensionsXY, position.y * Settings.tileDimensionsXY, Settings.tileDimensionsXY, Settings.tileDimensionsXY);
        }
    }

    public void update(Map map) {
        if ((current.size() == 0) && (tileType == TileType.ENTITY)){
            tileType = TileType.GROUND;
        }
        else
            current.forEach((GameObject go) -> {
            if (go != map.getPlayer())
                go.update(map);
        });
    }
}
