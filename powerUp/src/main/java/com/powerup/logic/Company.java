package com.powerup.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Company {

    private final String name;
    private final int initialValue;
    private boolean active;
    private ArrayList<Stock> stocks;
    private ArrayList<Tile> tiles;

    public Company(String name, int initialValue) {
        this.name = name;
        this.initialValue = initialValue;
        active = false;
        stocks = new ArrayList<>();
        tiles = new ArrayList<>();
        createStock();
    }

    private void createStock() {
        for (int i = 0; i < 25; i++) {
            stocks.add(new Stock(this, i));
        }
    }

    public void addTile(Tile tile) {
        if (tile.getPlayed()) {
            tile.setOwner(this);
            tiles.add(tile);
        }
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

    public String getName() {
        return name;
    }

    public int sellPrice() {
        // jokaisesta omistetusta tontista tulee 10% arvoa lisää
        // ja jokaisesta myydystä osingosta tulee 1%
        return initialValue + ((initialValue / 100) * ((25 - stocks.size()) + (tiles.size() * 10)));

    }

    public Stock sellStock() {
        if (stocks.isEmpty()) {
            return null;
        } else {
            Stock purchase = stocks.get(0);
            stocks.remove(0);
            return purchase;

        }
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void takeOver(Company company) {
        for (Tile tile : company.getTiles()) {
            this.addTile(tile);
        }
        company.liquidate();
    }

    public void liquidate() {
        tiles.clear();
        stocks.clear();
        createStock();
        active = false;
    }

}
