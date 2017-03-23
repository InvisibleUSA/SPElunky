package com.siemens.spe.spelunky.map;

import com.siemens.spe.spelunky.game.enemies.*;
import com.siemens.spe.spelunky.game.items.*;
import com.siemens.spe.spelunky.game.items.weapons.Dagger;
import com.siemens.spe.spelunky.game.items.weapons.TitaniumSpear;
import com.siemens.spe.spelunky.game.items.weapons.Whip;
import com.siemens.spe.spelunky.game.player.*;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import com.siemens.spe.spelunky.system.Settings;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Created by 152863eh on 21.03.2017.
 */
public class Map {
    private Tile[][] area;
    private Player player;

    public void loadMap(String filename) throws Exception {
        ArrayList<String> lines = Parser.parseMap(filename);
        area = new Tile[lines.get(0).length()][lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                area[j][i] = makeTile(lines.get(i).charAt(j), new Position(j,i));
            }
        }
    }

    private Tile makeTile(char c, Position pos) {
        Tile t = new Tile(pos);
        t.setTileType(TileType.ENTITY);
        switch (c) {
            case Parser.SYMBOL_BLOCK:
                t.setTileType(TileType.WALL);
                break;
            case Parser.SYMBOL_CHARACTER:
                player = new Player(pos.clone());
                t.addGameObject(player);
                break;
            case Parser.SYMBOL_ENEMY_BAT:
                t.addGameObject(new Bat(pos.clone()));
                break;
            case Parser.SYMBOL_ENEMY_BLUE_SLIME:
                t.addGameObject(new BlueSlime(pos.clone()));
                break;
            case Parser.SYMBOL_ENEMY_ORANGE_SLIME:
                t.addGameObject(new OrangeSlime(pos.clone()));
                break;
            case Parser.SYMBOL_ENEMY_ZOMBIE:
                t.addGameObject(new Zombie(pos.clone()));
                break;
            case Parser.SYMBOL_ITEM_DAGGER:
                t.addGameObject(new Dagger(pos.clone()));
                break;
            case Parser.SYMBOL_ITEM_LEATHER_ARMOR:
                t.addGameObject(new LeatherArmor(pos.clone()));
                break;
            case Parser.SYMBOL_ITEM_TITANIUM_SPEAR:
                t.addGameObject(new TitaniumSpear(pos.clone()));
                break;
            case Parser.SYMBOL_ITEM_WHIP:
                t.addGameObject(new Whip(pos.clone()));
                break;
            case Parser.SYMBOL_ITEM_EMPTY_HEART_CONTAINER:
                t.addGameObject(new EmptyHeartContainer(pos.clone()));
                break;
            case Parser.SYMBOL_ITEM_FILLED_HEART_CONTAINER:
                t.addGameObject(new FilledHeartContainer(pos.clone()));
                break;
            case Parser.SYMBOL_ITEM_RING_OF_MIGHT:
                t.addGameObject(new RingOfMight(pos.clone()));
                break;
            case Parser.SYMBOL_EXIT:
                t.setTileType(TileType.EXIT_STAIRS);
                break;
            case Parser.SYMBOL_EMPTY:
                t.setTileType(TileType.GROUND);
                break;
            default:
                throw new IllegalArgumentException("Symbol could not be resolved.");
        }
        return t;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getTileAt(int posX, int posY) {
        if (!isValidIndex(posX, posY))
            return null;
        return area[posX][posY];
    }
    public Tile getTileAt(Position pos) {
        return getTileAt(pos.x, pos.y);
    }

    /**
     * Returns an array with the boundary positions for rendering.
     * Index 0: x1 (left)
     * Index 1: y1 (top)
     * Index 2: x2 (right)
     * Index 3: y2 (bottom)
     * @return boundaries
     */
    private int[] getBoundaries() {
        int[] boundaries = new int[4];
        int x = player.getStats().position.x;
        int y = player.getStats().position.y;
        boundaries[0] = x - Settings.viewPort;
        if (boundaries[0] < 0) boundaries[0] = 0;
        boundaries[1] = y - Settings.viewPort;
        if (boundaries[1] < 0) boundaries[1] = 0;
        boundaries[2] = x + Settings.viewPort;
        if (boundaries[2] > area.length - 1) boundaries[2] = area.length - 1;
        boundaries[3] = y + Settings.viewPort;
        if (boundaries[3] > area[0].length - 1) boundaries[3] = area[0].length - 1;
        return boundaries;
    }

    public void moveEntity(Position src, GameObject entity) {
        Position dest = entity.getPosition();
        if (!isValidIndex(src) || !isValidIndex(dest)) throw new IndexOutOfBoundsException();
        if (area[dest.x][dest.y].getTileType() == TileType.WALL) throw new IllegalArgumentException("Destination is blocked");
        for (int i = 0; i < area[src.x][src.y].getCurrent().size(); i++) {
            if (area[src.x][src.y].getCurrent().get(i) == entity)
                area[src.x][src.y].getCurrent().remove(i);
        }
        if (area[src.x][src.y].getCurrent().size() == 0)
            area[src.x][src.y].setTileType(TileType.GROUND);
        area[dest.x][dest.y].addGameObject(entity);
        if (area[dest.x][dest.y].getTileType() == TileType.GROUND)
            area[dest.x][dest.y].setTileType(TileType.ENTITY);
    }

    public void remove(Position p, GameObject object) {
        remove(p.x, p.y, object);
    }
    public void remove(int x, int y, GameObject object) {
        for (int i = 0; i < area[x][y].getCurrent().size(); i++) {
            if (!area[x][y].getCurrent().get(i).getPosition().equals(x, y))
                throw new RuntimeException("Position is not equal to index. Map is wrong.");
            if (area[x][y].getCurrent().get(i) == object)
                area[x][y].getCurrent().remove(i);
        }
        if (isEmptyEntityTile(x,y))
            area[x][y].setTileType(TileType.GROUND);
    }

    public void draw(GraphicsContext gc) {
        int[] b = getBoundaries();
        for (int i = b[0]; i <= b[2]; i++) {
            for (int j = b[1]; j <= b[3]; j++) {
                area[i][j].draw(gc);
            }
        }
    }

    public void update() {
        player.update(this);
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                if (!area[i][j].getCurrent().contains(player)) {
                    area[i][j].update(this);
                }
            }
        }
    }

    public int getHeight() {
        return area[0].length;
    }

    public int getWidth() {
        return area.length;
    }

    private boolean isValidIndexX(int x) {
        return (x >= 0) && (x < area.length);
    }
    private boolean isValidIndexY(int y) {
        return (y >= 0) && (y < area[0].length);
    }

    private boolean isValidIndex(int x, int y) {
        return isValidIndexX(x) && isValidIndexY(y);
    }

    private boolean isValidIndex(Position pos) {
        return isValidIndex(pos.x, pos.y);
    }

    private boolean isEmptyEntityTile(int x, int y) {
        return (area[x][y].getTileType() == TileType.ENTITY) && (area[x][y].getCurrent().size() == 0);
    }
}