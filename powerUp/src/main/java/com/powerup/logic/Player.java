package com.powerup.logic;

import java.util.HashMap;

public class Player {

    private String name;
    // omaisuus
    private HashMap<Company, Integer> stocks;
    private int cash;

    public Player(String name) {
        this.name = name;
        stocks = new HashMap<>();
        cash = 5000;
    }

    public String getName() {
        return name;
    }

    public int getStock(Company company) {
        if (stocks.containsKey(company)) {
            return stocks.get(company);
        } else {
            return 0;
        }
    }

    public int buyStock(Company company) {
        return 0;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
