package com.powerup.logic;

/**
 * A pair of coordinates.
 *
 * @author Oliver Lewisohn
 * @since 2014-02-13
 */
public class Direction {

    private final int x;
    private final int y;

    /**
     * Creates a new direction with the given pair of coordinates.
     * @param x The x-coordinate of the new direction.
     * @param y The y-coordinate of the new direction.
     */
    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
