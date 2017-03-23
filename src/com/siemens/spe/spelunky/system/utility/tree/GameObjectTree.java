package com.siemens.spe.spelunky.system.utility.tree;

import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by 152544be on 21.03.2017.
 */
public class GameObjectTree implements GameObject {
    GameObjectTreeNode root;

    public GameObjectTree(GameObject root) {
        this.root = new GameObjectTreeNode();
        this.root.setObject(root);
    }

    public void addObject(GameObject obj, GameObjectTreeNode parent) {
        GameObjectTreeNode node = new GameObjectTreeNode();
        node.setObject(obj);
        parent.addChild(node);
    }

    public void draw(GraphicsContext gc) {
        root.draw(gc);
    }

    public void update(Map map) {
        root.update(map);
    }

    @Override
    public Position getPosition() {
        return new Position(-1, -1);
    }

}
