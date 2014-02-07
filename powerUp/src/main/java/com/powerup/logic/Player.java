package com.powerup.logic;

import java.util.ArrayList;

/**
 * A player of the game. Has cash and can use it to buy shares.
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public class Player {

    private String name;
    private ArrayList<Share> shares;
    private int cash;

    public Player(String name) {
        this.name = name;
        shares = new ArrayList<>();
        cash = 5000;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfShares(Company company) {
        int i = 0;
        for (Share share : shares) {
            if (share.getCompany() == company) {
                i++;
            }
        }
        return i;
    }

    public void buyShare(Company company) {
        int price = company.sellPrice();
        if (this.cash >= price) {
            Share purchase = company.sellShare();
            if (purchase != null) {
                cash -= price;
                shares.add(purchase);
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
