package com.powerup.logic;

public class Stock {

    private final Company company;
    private final int id;
    
    public Stock(Company company, int id) {
        this.company = company;
        this.id = id;
    }
    
    public Company getCompany() {
        return company;
    }

}
