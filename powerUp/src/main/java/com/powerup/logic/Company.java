package com.powerup.logic;

import java.util.ArrayList;

public class Company {

    private final String name;
    private final int initialValue;
    private Board board;
    private boolean status;
    private ArrayList<Stock> stocks;

    public Company(String name, int initialValue, Board board) {
        this.name = name;
        this.initialValue = initialValue;
        this.board = board;
        status = false;
        stocks = new ArrayList<>();
        createStock();
    }
    
    private void createStock() {
        for (int i = 0; i < 25; i++) {
            stocks.add(new Stock(this, i));
        }
    }

    public void setStatus(boolean status) {
        this.status = status;
    }    
    
    public String getName() {
        return name;
    }

    public void getValue() {
        System.out.println(this);
    }

    public Stock buyStock() {
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
}
