package com.powerup.logic;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    // kaksiulotteinen taulukko, johon laitetaan pelin tontit
    private final ArrayList<ArrayList<Tile>> tiles;

    // tällä hetkellä pelattavat tontit
    private ArrayList<Tile> playableTiles;

    private final Random random;

    public Board() {
        tiles = new ArrayList<>();
        random = new Random();
        playableTiles = new ArrayList<>();
        createTiles();
        playStartingTiles();
        chooseFirstPlayableTiles();

    }

    private void createTiles() {
        for (int i = 0; i < 10; i++) {
            tiles.add(new ArrayList<Tile>());
            for (int j = 0; j < 10; j++) {
                tiles.get(i).add(new Tile(i, j));
            }
        }
    }

    private void playStartingTiles() {
        int x;
        int y;
        int j = 0;
        while (j < 5) {
            x = random.nextInt(10);
            y = random.nextInt(10);
            if (!getTile(x, y).getPlayed()) {
                playTile(x, y);
                j++;
            }
        }
    }

    private void chooseFirstPlayableTiles() {
        int x;
        int y;
        int j = 0;
        while (j < 10) {
            x = random.nextInt(10);
            y = random.nextInt(10);
            if (!getTile(x, y).getPlayed()) {
                playableTiles.add(getTile(x, y));
                j++;
            }
        }
    }

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

    public boolean playTile(int x, int y) {
        if ((x >= 0) && (x <= 9) && (y >= 0) && (y <= 9)) {
            getTile(x, y).play();
            return true;
        }
        return false;
    }

    public ArrayList<Tile> getNeighbours(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        ArrayList<Tile> neighbours = new ArrayList<>();
        if ((getTile(x - 1, y) != null) && (getTile(x - 1, y).getPlayed() == true)) {
            neighbours.add(getTile(x - 1, y));
        }
        if ((getTile(x + 1, y) != null) && (getTile(x + 1, y).getPlayed() == true)) {
            neighbours.add(getTile(x + 1, y));
        }
        if ((getTile(x, y - 1) != null) && (getTile(x, y - 1).getPlayed() == true)) {
            neighbours.add(getTile(x, y - 1));
        }
        if ((getTile(x, y + 1) != null) && (getTile(x, y + 1).getPlayed() == true)) {
            neighbours.add(getTile(x, y + 1));
        }
        return neighbours;
    }
}
