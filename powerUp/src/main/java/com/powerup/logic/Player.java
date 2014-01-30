package com.powerup.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private String name;
    // omaisuus
    private ArrayList<Stock> stocks;
    private int cash;

    public Player(String name) {
        this.name = name;
        stocks = new ArrayList<>();
        cash = 5000;
    }

    public String getName() {
        return name;
    }

    public int getStockAmount(Company company) {
        int i = 0;
        for (Stock stock : stocks) {
            if (stock.getCompany() == company) {
                i++;
            }
        }
        return i;
    }

    public void buyStock(Company company) {
        int price = company.sellPrice();
        if (this.cash >= price) {
            Stock purchase = company.sellStock();
            if (purchase != null) {
                cash -= price;
                stocks.add(purchase);
            }
        }
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
