package com.powerup.logic;

import java.util.ArrayList;

/**
 * A player of the game. Has cash and can use it to buy shares.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public class Player implements Comparable<Player> {

    private String name;
    private ArrayList<Share> shares;
    private Tile[] tiles;
    private int cash;

    public Player() {
        shares = new ArrayList<>();
        tiles = new Tile[5];
        cash = 5000;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean giveTile(Tile tile) {
        for (int i = 0; i < 5; i++) {
            if (tiles[i] == null) {
                tiles[i] = tile;
                tile.setLocation(Tile.Location.HAND);
                return true;
            }
        }
        return false;
    }

//    public Tile getTile(int n) {
//        if ((n >= 0) && (n <= 4)) {
//            return tiles[n];
//        }
//        return null;
//    }
    public Tile playTile(int n) {
        if ((n >= 0) && (n <= 4)) {
            Tile ret = tiles[n];
            tiles[n] = null;
            return ret;
        }
        return null;
    }

    public int getNumberOfShares(Company company) {
        int i = 0;
        for (Share share : shares) {
            if (share.getCompany() == company) {
                i++;
            }
        }
        return i;
    }

    public void buyShare(Company company) {
        int price = company.sellPrice();
        if (this.cash >= price) {
            Share purchase = company.sellShare();
            if (purchase != null) {
                cash -= price;
                shares.add(purchase);
            }
        }
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public Tile getTile(int n) {
        if ((n >= 0) && (n <= 5)) {
            return tiles[n];
        }
        return null;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    @Override
    public int compareTo(Player o) {
        return this.tiles[0].compareTo(o.tiles[0]);
    }
}
