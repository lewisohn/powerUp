package com.powerup.logic;

public class Company {

    private final String name;
    private int value;
    private int stock;

    public Company(String name, int value) {
        this.name = name;
        this.value = value;
        this.stock = 25;
    }

    public int getValue() {
        return value;
    }

    public boolean buyStock() {
        if (stock == 0) {
            return false;
        } else {
            stock--;
            return true;
        }
    }

    public void setStock(int amount) {
        this.stock = Math.max(amount, 25);
    }

    public int getStock() {
        return stock;
    }
}
