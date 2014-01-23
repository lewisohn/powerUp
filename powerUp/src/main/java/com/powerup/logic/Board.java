package com.powerup.logic;

import java.util.ArrayList;

public class Board {

    // kaksiulotteinen taulukko, johon laitetaan pelin tontit
    private final ArrayList<ArrayList<Tile>> tiles;

    public Board() {
        tiles = new ArrayList<>();
        createTiles();
    }

    private void createTiles() {
        for (int i = 0; i < 10; i++) {
            tiles.add(new ArrayList<Tile>());
            for (int j = 0; j < 10; j++) {
                tiles.get(i).add(new Tile(i, j));
            }
        }
    }

    public Tile getTile(int x, int y) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            throw new IllegalArgumentException();
        } else {
            return tiles.get(x).get(y);
        }
    }

    public boolean playTile(int x, int y) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            return false;
        } else {
            getTile(x, y).play();
            return true;
        }
    }

    public boolean assignTile(int x, int y, Company company) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            return false;
        } else if (!getTile(x, y).getPlayed()) {
            return false;
        } else {
            getTile(x, y).setOwner(company);
            return true;
        }
    }

    public ArrayList<Tile> getNeighbours(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        ArrayList<Tile> neighbours = new ArrayList<>();
        if (tile.getX() > 0) {
            if (getTile(x - 1, y).getPlayed() == true) {
                neighbours.add(getTile(x - 1, y));
            }
        }
        if (tile.getX() < 9) {
            if (getTile(x + 1, y).getPlayed() == true) {
                neighbours.add(getTile(x + 1, y));
            }
        }
        if (tile.getY() > 0) {
            if (getTile(x, y - 1).getPlayed() == true) {
                neighbours.add(getTile(x, y - 1));
            }
        }
        if (tile.getY() < 9) {
            if (getTile(x, y + 1).getPlayed() == true) {
                neighbours.add(getTile(x, y + 1));
            }
        }
        return neighbours;
    }

    public ArrayList<Tile> getCompanyTiles(Company company) {
        ArrayList<Tile> companyLand = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Company x = getTile(i, j).getOwner();
                if ((x != null) && (x.equals(company))) {
                    companyLand.add(getTile(i, j));
                }
            }
        }
        return companyLand;
    }
}
