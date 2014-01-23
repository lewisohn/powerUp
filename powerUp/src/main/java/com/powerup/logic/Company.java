package com.powerup.logic;

public class Company {

    private final String name;
    private final int initialValue;
    private int stock;
    private Board board;

    public Company(String name, int initialValue, Board board) {
        this.name = name;
        this.initialValue = initialValue;
        this.stock = 25;
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public void getValue() {
        System.out.println(this);
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
