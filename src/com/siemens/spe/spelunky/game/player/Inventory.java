package com.siemens.spe.spelunky.game.player;

import com.siemens.spe.spelunky.game.items.Item;
import com.siemens.spe.spelunky.game.items.weapons.Weapon;
import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.map.TileType;
import com.siemens.spe.spelunky.system.Position;
import com.siemens.spe.spelunky.ui.UIMessageBox;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by 154374ln on 21.03.2017.
 */
public class Inventory
{
    private Item[] inventory = new Item[5];
    private boolean inventoryFull = false;
    private int dcnt = 0;

    /**
     * Adds @param item to first free spot in Inventory
     *
     * @param item
     */
    void addToInv(Item item, Map map, Position pos)
    {
        for (int i = 0; i < 5; i++)
        {
            if (inventory[i] == null)
            {
                if (item instanceof Weapon)
                {

                }
                inventory[i] = item;
                map.remove(item.getPosition().x, item.getPosition().y, item);
                item.getStats().position = new Position(-1,-1);
                inventoryFull = false;
                return;
            } else
            {
                inventoryFull = true;
                dcnt = 0;
            }
        }
    }

    public Item getItemAt(int index)
    {
        return inventory[index];
    }

    Item useItemAt(int index)
    {
        Item tmp = inventory[index];
        removeItem(index);
        return tmp;
    }

    public void draw(GraphicsContext gc)
    {
        if (inventoryFull && dcnt < 20)
        {
            UIMessageBox box = new UIMessageBox("Inventory full to drop Item press Q + item position");
            box.draw(gc);
            //gc.fillText("Inventory full to drop Item press Q + item position", 0, 0);
            dcnt++;
        }
    }

    /**
     * Sets item at @param index to null
     *
     * @param index
     */
    public void dropItem(int index, Map map, Player player)
    {
        Position pos = player.getPosition();
        if (map.getTileAt(pos.x + 1, pos.y).getTileType() == TileType.GROUND)
        {
            inventory[index].getStats().position = new Position(player.getPosition().x + 1, player.getPosition().y);
        }
        else if (map.getTileAt(pos.x - 1, pos.y).getTileType() == TileType.GROUND)
        {
            inventory[index].getStats().position = new Position(player.getPosition().x - 1, player.getPosition().y);
        }
        else if (map.getTileAt(pos.x, pos.y + 1).getTileType() == TileType.GROUND)
        {
            inventory[index].getStats().position = new Position(player.getPosition().x, player.getPosition().y + 1);
        }
        else if (map.getTileAt(pos.x, pos.y - 1).getTileType() == TileType.GROUND)
        {
            inventory[index].getStats().position = new Position(player.getPosition().x, player.getPosition().y - 1);
        }
        map.getTileAt(inventory[index].getPosition()).addGameObject(inventory[index]);
        inventory[index] = null;
    }

    public void removeItem(int index)
    {
        inventory[index] = null;
    }
}
