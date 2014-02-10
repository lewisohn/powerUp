package com.powerup.logic;

import java.awt.Color;
import java.util.ArrayList;

/**
 * An electricity company. <p /> Initially inactive, but is activated during the
 * course of the game. Once activated, owns tiles and sells shares. Can take
 * over other companies. Becomes inactivate once again if taken over.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Company implements Comparable<Company> {

    private final String name;
    private final int initialValue;
    private boolean active;
    private Tile headquarters;
    private ArrayList<Share> shares;
    private ArrayList<Tile> tiles;
    private Game game;
    private Color color;

    /**
     * Creates a new inactive company with the given name, price, and 25 shares.
     *
     * @param name The name of the company.
     * @param initialValue The initial share price of the company.
     */
    public Company(String name, int initialValue, Game game, Color color) {
        this.name = name;
        this.initialValue = initialValue;
        this.game = game;
        this.color = color;
        active = false;
        shares = new ArrayList<>();
        tiles = new ArrayList<>();
        createShares();
    }

    private void createShares() {
        for (int i = 0; i < 25; i++) {
            shares.add(new Share(this));
        }
    }

    /**
     * Assigns a tile to the company if it has already been played.
     *
     * @param tile The tile to be added.
     */
    public void addTile(Tile tile) {
        if (tile.getLocation() == Tile.Location.BOARD) {
            tile.setOwner(this);
            tiles.add(tile);
        }
    }

    public int size() {
        return tiles.size();
    }

    public Tile getHeadquarters() {
        return headquarters;
    }

    public Color getColor() {
        return this.color;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Calculates the price of buying one share from the company. <p /> A
     * company's value grows by 10 percentage points for each tile it owns in
     * addition to the minimum of two tiles.
     *
     * @return The price of buying one share from the company.
     */
    public int sellPrice() {
        return initialValue + ((initialValue / 100) * (Math.max(0, tiles.size() - 2) * 10));

    }

    public Share sellShare() {
        if (shares.isEmpty()) {
            return null;
        } else {
            Share purchase = shares.get(0);
            shares.remove(0);
            return purchase;

        }
    }

    public ArrayList<Share> getShares() {
        return shares;
    }

    /**
     * Makes one company take over another one, gaining all its tiles.
     *
     * @param company The company to be taken over.
     */
    public void takeOver(Company company) {
        for (Tile tile : company.getTiles()) {
            this.addTile(tile);
        }
        company.liquidate();

    }

    private void liquidate() {
        tiles.clear();
        shares.clear();
        createShares();
        active = false;
    }

    public void activate(Tile tile) {
        headquarters = tile;
        for (Tile t : game.getBoard().getAllConnectedTiles(tile)) {
            addTile(t);
        }
        active = true;
    }

    @Override
    public int compareTo(Company o) {
        return this.size() - o.size();
    }
}
