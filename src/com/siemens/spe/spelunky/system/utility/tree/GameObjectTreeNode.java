package com.siemens.spe.spelunky.system.utility.tree;

import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Created by 152544be on 21.03.2017.
 */
class GameObjectTreeNode implements GameObject{
    private ArrayList<GameObjectTreeNode> children;
    private GameObjectTreeNode parent;
    private GameObject object;

    public GameObjectTreeNode() {
        children = new ArrayList<GameObjectTreeNode>();
    }

    public GameObjectTreeNode(GameObjectTreeNode parent) {
        this();
        setParent(parent);
    }

    public void setObject(GameObject object) {
        this.object = object;
    }

    public void addChild(GameObjectTreeNode child) {
        child.setParent(this);
        children.add(child);
    }

    public void setParent(GameObjectTreeNode parent) {
        this.parent = parent;
        parent.addChild(this);
    }

    public void draw(GraphicsContext gc) {
        if (object != null) {
            object.draw(gc);
        }
        for (GameObjectTreeNode child : children) {
            child.object.draw(gc);
        }
    }

    public void update(Map map) {
        if (object != null) {
            object.update(map);
        }
        for (GameObjectTreeNode child : children) {
            child.object.update(map);
        }
    }

    @Override
    public Position getPosition() {
        return new Position(-1,-1);
    }
}