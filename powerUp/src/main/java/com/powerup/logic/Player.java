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
    private final ArrayList<Share> shares;
    private final Tile[] tiles;
    private int cash;

    /**
     * Creates a new player with $5000 and space for five tiles.
     */
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

    /**
     * Adds a specified tile to the player's hand, providing there is room.
     * @param tile The tile to be added.
     * @return True if there was room for the tile, otherwise false.
     */
    public boolean addTileToHand(Tile tile) {
        for (int i = 0; i < 5; i++) {
            if (tiles[i] == null) {
                tiles[i] = tile;
                tile.setLocation(Tile.Location.HAND);
                return true;
            }
        }
        return false;
    }

    /**
     * Takes a tile from the player's hand and returns it.
     * @param n The number of the tile to be taken, from 0 to 4.
     * @return The tile which has been taken.
     */
    public Tile returnTileFromHand(int n) {
        if ((n >= 0) && (n < 5)) {
            if (tiles[n] == null) {
                return null;
            } else {
                Tile t = tiles[n];
                tiles[n] = null;
                return t;
            }
        }
        return null;
    }

    /**
     * Finds out how many shares the player owns in a given company.
     * @param company The company to be checked.
     * @return The number of shares the player owns in the given company.
     */
    public int getNumberOfShares(Company company) {
        int i = 0;
        for (Share share : shares) {
            if (share.getCompany() == company) {
                i++;
            }
        }
        return i;
    }

    /**
     * Instructs the player to buy a share in the given company.
     * @param company The company to buy a share from.
     * @param freebie Whether or not the player has to pay for buying the share.
     * When creating a company, the first share is free.
     * @return Whether or not a share could be bought with the player's current cash.
     */
    public boolean buyShare(Company company, boolean freebie) {
        int price;
        if (!freebie) {
            price = company.sellPrice();
        } else {
            price = 0;
        }
        if (this.cash >= price) {
            Share purchase = company.sellShare();
            if (purchase != null) {
                cash -= price;
                shares.add(purchase);
                return true;
            }
        }
        return false;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    /**
     * Fetches a tile from the player's hand without removing it.
     * @param n The number of the tile to be fetched, from 0 to 4.
     * @return The desired tile (can be null).
     */
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
        if (this.cash == o.getCash()) {
            return this.tiles[0].compareTo(o.getTiles()[0]);
        } else {
            return o.getCash() - this.cash;
        }
    }

    /**
     * Finds out how many tiles a player currently has in their hand.
     * @return The size of the player's hand.
     */
    public int getHandSize() {
        int i = 0;
        for (Tile tile : tiles) {
            if (tile != null) {
                i++;
            }
        }
        return i;
    }

    /**
     * Gives the player a certain amount of cash.
     * @param amount
     */
    public void giveCash(int amount) {
        setCash(getCash() + amount);
    }

    /**
     * Instructs the player to sell all their shares in a given company.
     * @param prey The company (being taken over) whose shares should be sold.
     * @return The cash generated through the sale.
     */
    public int sellShares(Company prey) {
        int income = 0;
        ArrayList<Share> remove = new ArrayList<>();
        for (Share share : shares) {
            if (share.getCompany() == prey) {
                income += prey.sellPrice();
                remove.add(share);
            }
        }
        for (Share share : remove) {
            shares.remove(share);
        }
        giveCash(income);
        return income;
    }
}
