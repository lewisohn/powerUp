package com.powerup.logic;

/**
 * A tile on the game board.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public class Tile implements Comparable<Tile> {

    private Location location;
    private Company owner;
    private int x;
    private int y;

    /**
     * Creates a new tile with the given coordinates.
     *
     * @param x The x-coordinate of the tile.
     * @param y THe y-coordinate of the tile.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        location = Location.NONE;
        owner = null;
    }

    public enum Location {

        BOARD,
        HAND,
        NONE
    }

    /**
     * Plays a tile to the board. This cannot be undone.
     */
    public void setLocation(Location loc) {
        this.location = loc;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location getLocation() {
        return location;
    }

    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company company) {
        owner = company;
    }

    @Override
    public String toString() {
        String ref = new String();
        ref = ref + (char) (x + 65);
        ref = ref + (9 - y);
        return ref;
    }

    @Override
    public int compareTo(Tile o) {
        if (this.x < o.getX()) {
            return -1;
        } else if (this.x == o.getX()) {
            if (this.y < o.getY()) {
                return -1;
            }
            return 1;
        }
        return 1;
    }

    public boolean isNextTo(Tile tile) {
        if ((this.x == tile.getX()) && (Math.abs(this.y - tile.getY()) == 1)) {
            return true;
        } else if ((this.y == tile.getY()) && (Math.abs(this.x - tile.getX()) == 1)) {
            return true;
        } else {
            return false;
        }
    }
}
