package com.powerup.logic;

/**
 * A tile on the game board.
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public class Tile {

    private boolean played;
    private Company owner;
    private int x;
    private int y;

    /**
     * Creates a new tile with the given coordinates.
     * @param x The x-coordinate of the tile.
     * @param y THe y-coordinate of the tile.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        played = false;
        owner = null;
    }

    /**
     * Plays a tile to the board. This cannot be undone.
     */
    public void play() {
        played = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getPlayed() {
        return played;
    }

    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company company) {
        owner = company;
    }

    public String toString() {
        String ref = new String();
        ref = ref + (char) (x + 65);
        ref = ref + y;
        return ref;
    }
}
