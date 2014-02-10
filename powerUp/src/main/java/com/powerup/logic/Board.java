package com.powerup.logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Responsible for maintaining the game's tiles.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Board {

    private final ArrayList<ArrayList<Tile>> tiles;
    private ArrayList<Tile> playableTiles;
    private final Random random;
    private ArrayList<Tile> allNeighbours = new ArrayList<>();

    /**
     * Creates 100 tiles and picks five of them to be played at the start.
     */
    public Board() {
        tiles = new ArrayList<>();
        random = new Random();
        playableTiles = new ArrayList<>();
        createTiles();
//        chooseNewPlayableTiles(5);

    }

    private void createTiles() {
        for (int i = 0; i < 10; i++) {
            tiles.add(new ArrayList<Tile>());
            for (int j = 0; j < 10; j++) {
                tiles.get(i).add(new Tile(i, j, this));
            }
        }
    }

    /**
     * Plays five tiles to start the game off. <p /> This method may be
     * deprecated in future, as each player will draw one initial tile, with the
     * player drawing closest to A0 going first.
     */
//    public void playStartingTiles() {
//        selectTiles(5, true);
//    }
    /**
     * Allocates a number of new playable tiles.
     *
     * @param n The number of new tiles to be allocated.
     */
//    public void chooseNewPlayableTiles(int n) {
//        selectTiles(n, false);
//    }
    public boolean unassignedTilesRemain() {
        for (ArrayList<Tile> arrayList : tiles) {
            for (Tile tile : arrayList) {
                if (tile.getLocation() == Tile.Location.NONE) {
                    return true;
                }
            }
        }
        return false;
    }

    public Tile getRandomUnassignedTile() {
        if (!unassignedTilesRemain()) {
            return null;
        } else {
            int x;
            int y;
            while (true) {
                x = random.nextInt(10);
                y = random.nextInt(10);
                if (getTile(x, y).getLocation() == Tile.Location.NONE) {
                    return getTile(x, y);
                }
            }
        }
    }

    public boolean giveTileToPlayer(Player player) {
        if (unassignedTilesRemain()) {
            if (player.giveTile(getRandomUnassignedTile())) {
                return true;
            }
        }
        return false;
    }

    public boolean giveTileToPlayer(Player player, Tile tile) {
        if (unassignedTilesRemain()) {
            if (player.giveTile(tile)) {
                return true;
            }
        }
        return false;
    }

//    private void selectTiles(int n, boolean play) {
//        int x;
//        int y;
//        int i = 0;
//        while (i < n) {
//            x = random.nextInt(10);
//            y = random.nextInt(10);
//            if (play) {
//                if (!getTile(x, y).getState()) {
//                    playTile(x, y);
//                    i++;
//                }
//            } else {
//                if (!playableTiles.contains(getTile(x, y))) {
//                    playableTiles.add(getTile(x, y));
//                    i++;
//                }
//
//            }
//        }
//    }
    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public ArrayList<Tile> getPlayableTiles() {
        return playableTiles;
    }

    public Tile getTile(int x, int y) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            return null;
        } else {
            return tiles.get(x).get(y);
        }
    }

    /**
     * Plays a tile to the board.
     *
     * @param x The x-coordinate of the tile to be played.
     * @param y The y-coordinate of the tile to be played.
     * @return True if the tile was able to be played, false otherwise.
     */
    public boolean playTileToBoard(int x, int y) {
        if ((x >= 0) && (x <= 9) && (y >= 0) && (y <= 9)) {
            getTile(x, y).setLocation(Tile.Location.BOARD);
            return true;
        }
        return false;
    }

    public boolean playTileToBoard(Tile tile) {
        if (tile.getLocation() != Tile.Location.BOARD) {
            tile.setLocation(Tile.Location.BOARD);
            return true;
        }
        return false;
    }

    /**
     * Finds which neighbouring tiles of a given tile have been played.
     *
     * @param tile The tile whose neighbours are to be fetched.
     * @return A list containing the played neighbours of the parameter tile.
     */
    public ArrayList<Tile> getNeighbours(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        ArrayList<Tile> neighbours = new ArrayList<>();
        if ((getTile(x - 1, y) != null) && (getTile(x - 1, y).getLocation() == Tile.Location.BOARD)) {
            neighbours.add(getTile(x - 1, y));
        }
        if ((getTile(x + 1, y) != null) && (getTile(x + 1, y).getLocation() == Tile.Location.BOARD)) {
            neighbours.add(getTile(x + 1, y));
        }
        if ((getTile(x, y - 1) != null) && (getTile(x, y - 1).getLocation() == Tile.Location.BOARD)) {
            neighbours.add(getTile(x, y - 1));
        }
        if ((getTile(x, y + 1) != null) && (getTile(x, y + 1).getLocation() == Tile.Location.BOARD)) {
            neighbours.add(getTile(x, y + 1));
        }
        return neighbours;
    }
    private ArrayList<Tile> ret;

    public ArrayList<Tile> getAllConnectedTiles(Tile tile) {
        ret = new ArrayList<>();
        recursivelyFindNeighbours(tile);
        return ret;
    }

    private void recursivelyFindNeighbours(Tile tile) {
        ret.add(tile);
        for (Tile t : getNeighbours(tile)) {
            if (!ret.contains(t)) {
                recursivelyFindNeighbours(t);
            }
        }
    }
}
