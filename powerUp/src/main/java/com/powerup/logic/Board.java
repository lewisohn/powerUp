package com.powerup.logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * The game's board, responsible for maintaining tiles.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Board {

    private final ArrayList<ArrayList<Tile>> tiles;
    private final Random random;
    private Tile highlightedTile;

    /**
     * Creates 100 tiles and sets up a random integer generator.
     */
    public Board() {
        tiles = new ArrayList<>();
        random = new Random();
        highlightedTile = null;
        createTiles();
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
     * Finds out how many tiles are not yet on the board or in a player's hand.
     *
     * @return The number of tiles remaining in the "bank".
     */
    public int unassignedTilesRemaining() {
        int sum = 0;
        for (ArrayList<Tile> arrayList : tiles) {
            for (Tile tile : arrayList) {
                if (tile.getLocation() == Tile.Location.NONE) {
                    sum++;
                }
            }
        }
        return sum;
    }

    /**
     * Gets a random tile which is not yet on the board or in a player's hand.
     *
     * @return A new random tile.
     */
    public Tile getRandomUnassignedTile() {
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

    /**
     * Puts a random unassigned tile in a player's hand.
     *
     * @param player The player to receive the tile.
     */
    public void giveTileToPlayer(Player player) {
        if (unassignedTilesRemaining() > 0) {
            giveTileToPlayer(player, getRandomUnassignedTile());
        }
    }

    /**
     * Puts a specific tile in a player's hand.
     *
     * @param player The player to receive the tile.
     * @param tile The tile to be given.
     */
    public void giveTileToPlayer(Player player, Tile tile) {
        if (player.addTileToHand(tile)) {
        }

    }

    /**
     * Refills a player's hand to size five, providing enough tiles remain.
     *
     * @param player The player to receive the tiles.
     */
    public void refillPlayerHand(Player player) {
        for (int i = player.getHandSize(); i < 5; i++) {
            giveTileToPlayer(player);
        }
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    /**
     * Gets a tile by its x and y coordinates.
     *
     * @param x The x-coordinate of the tile to be fetched.
     * @param y The y-coordinate of the tile to be fetched.
     * @return The desired tile, providing the coordinates are within range.
     */
    public Tile getTile(int x, int y) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            return null;
        } else {
            return tiles.get(x).get(y);
        }
    }

    /**
     * Plays a tile with given coordinates to the board.
     *
     * @param x The x-coordinate of the tile to be played.
     * @param y The y-coordinate of the tile to be played.
     * @return False if the tile was already on the board, otherwise true.
     */
    public boolean playTileToBoard(int x, int y) {
        if ((x >= 0) && (x <= 9) && (y >= 0) && (y <= 9)) {
            getTile(x, y).setLocation(Tile.Location.BOARD);
            return true;
        }
        return false;
    }

    /**
     * Plays a given tile to the board.
     *
     * @param tile The tile to be played.
     * @return False if the tile was already on the board, otherwise true.
     */
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
        Direction[] directions = new Direction[]{
            new Direction(-1, 0), new Direction(1, 0), new Direction(0, -1), new Direction(0, 1)
        };
        for (Direction d : directions) {
            Tile t = getTile(x + d.getX(), y + d.getY());
            if ((t != null) && (t.getLocation() == Tile.Location.BOARD)) {
                neighbours.add(t);
            }
        }
        return neighbours;
    }

    /**
     * Finds all the connecting tiles of a given tile. <p /> This differs from
     * getNeighbours in that it also finds the neighbours of all the original
     * tile's neighbours, all their neighbours, and so on.
     *
     * @param tile The tile whose connecting tiles are to be fetched.
     * @return A list containing every tile connected to the parameter tile.
     */
    public ArrayList<Tile> getAllConnectedTiles(Tile tile) {
        ArrayList<Tile> list = new ArrayList<>();
        recursivelyFindNeighbours(tile, list);
        return list;
    }

    private void recursivelyFindNeighbours(Tile tile, ArrayList<Tile> list) {
        list.add(tile);
        for (Tile t : getNeighbours(tile)) {
            if (!list.contains(t)) {
                recursivelyFindNeighbours(t, list);
            }
        }
    }

    /**
     * Fetches the companies which own tiles neighbouring the parameter tile.
     *
     * @param tile The tile to be checked.
     * @return A list of companies which own tiles neighbouring the parameter
     * tile.
     */
    public ArrayList<Company> companyNeighbours(Tile tile) {
        ArrayList<Company> companies = new ArrayList<>();
        for (Tile t : getNeighbours(tile)) {
            if ((t.getOwner() != null) && (!companies.contains(t.getOwner()))) {
                companies.add(t.getOwner());
            }
        }
        return companies;
    }

    /**
     * Finds out how many neutral tiles neighbour the parameter tile. A neutral
     * tile is defined as a tile which is not owned by any company.
     *
     * @param tile The tile to be checked.
     * @return The number of neutral tiles neighbouring the parameter tile.
     */
    public int neutralNeighbours(Tile tile) {
        int neutral = 0;
        for (Tile t : getNeighbours(tile)) {
            if (t.getOwner() == null) {
                neutral++;
            }
        }
        return neutral;
    }

    public void setHighlightedTile(Tile tile) {
        highlightedTile = tile;
    }

    public Tile getHighlightedTile() {
        return highlightedTile;
    }
}
