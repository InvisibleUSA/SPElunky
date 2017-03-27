package com.siemens.spe.spelunky.system;

/**
 * Created by 153627vk on 21.03.2017.
 */

public final class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(int x, int y) {
        return ((this.x == x) && (this.y == y));
    }
    @Override
    public boolean equals(Object obj) {
        return ((x == ((Position) obj).x) && (y == ((Position) obj).y));
    }

    public Position clone()
    {
        return new Position(x,y);
    }
}
