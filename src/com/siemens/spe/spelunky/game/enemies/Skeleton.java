package com.siemens.spe.spelunky.game.enemies;

import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.Position;

/**
 * Created by Erik on 31.03.2017.
 */
public class Skeleton extends Enemy {
    private boolean movedPrev;
    private static final int SKELETON_AGGRO_RADIUS = 12;

    public Skeleton(Position pos) {
        super(1, "Skeleton", 1, pos, new Position(24, 26), 8);
        movedPrev = false;
    }

    @Override
    protected void move(Map map) {
        if (hasMoved)
            movedPrev = !movedPrev;
        if (movedPrev) return;
        Position pos = findPath(map);
        moveTo(map, pos.x, pos.y);
    }

    private Position findPath(Map map) {
        int[][] area = new int[map.getWidth()][map.getHeight()];
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                area[i][j] = -1;
            }
        }
        Position posPlayer = map.getPlayer().getPosition(), posSk = getPosition();
        area[posPlayer.x][posPlayer.y] = 0;
        int dist = -1;
        while ((area[posSk.x][posSk.y] == -1) && (dist < SKELETON_AGGRO_RADIUS)) {
            dist++;
            for (int i = 0; i < area.length - 1; i++) {
                for (int j = 0; j < area[i].length - 1; j++) {
                    if (!map.getTileAt(i, j).blocksEnemies() || posSk.equals(i,j)) {
                        if ((checkPos(area, i - 1, j) == dist) ||
                                (checkPos(area, i + 1, j) == dist) ||
                                (checkPos(area, i, j - 1) == dist) ||
                                (checkPos(area, i, j + 1) == dist)) {
                            if ((area[i][j] == -1) || (area[i][j] > dist + 1)) {
                                area[i][j] = dist + 1;
                            }
                        }
                    }
                }
            }
        }

        if (checkPos(area, posSk.x - 1, posSk.y) == dist)
            return new Position(posSk.x - 1, posSk.y);
        if (checkPos(area, posSk.x + 1, posSk.y) == dist)
            return new Position(posSk.x + 1, posSk.y);
        if (checkPos(area, posSk.x, posSk.y - 1) == dist)
            return new Position(posSk.x, posSk.y - 1);
        if (checkPos(area, posSk.x, posSk.y + 1) == dist)
            return new Position(posSk.x, posSk.y + 1);
        return getPosition();
    }

    private int checkPos(int[][] area, int i, int j) {
        if ((i < 0) || (j < 0)) return -1;
        if ((i > area.length - 1) || (j > area[i].length - 1)) return -1;
        return area[i][j];
    }
}
