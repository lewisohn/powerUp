package com.powerup.logic;

public class Plot {

    // koordinaatit
    private int x;
    private int y;
    private boolean played;
    private Company owner;

    public Plot(int x, int y) {
        this.x = x;
        this.y = y;
        played = false;
        owner = null;
    }

    public void play() {
        played = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean played() {
        return played;
    }

    public Company getOwner() {
        return owner;
    }
    
    public void setOwner(Company company) {
        owner = company;
    }

}
