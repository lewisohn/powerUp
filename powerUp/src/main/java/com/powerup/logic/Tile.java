package com.powerup.logic;

public class Tile {

    // koordinaatit
    private int x;
    private int y;
    private boolean played;
    private Company owner;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        played = false;
        owner = null;
    }

    // kun tiili on pelattu laudalle, sitä ei voi ottaa pois
    public void play() {
        played = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getPlayed() {
        return played;
    }

    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company company) {
        owner = company;
    }

    public String getRef() {
        String ref = new String();
        ref = ref + (char) (x + 65);
        ref = ref + y;
        return ref;
    }
}
