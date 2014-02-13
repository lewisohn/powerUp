package com.powerup.logic;

import java.awt.Color;
import java.util.ArrayList;

public class Market {

    private Company[] companies;
    private final Board board;

    public Market(Board board) {
        this.board = board;
        createCompanies();
    }

    private void createCompanies() {
        companies = new Company[]{
            new Company("Eclipse Solar", 200, this, new Color(230, 230, 23)),
            new Company("Maniac Timber", 200, this, new Color(23, 230, 23)),
            new Company("Cortex Power", 300, this, new Color(46, 46, 230)),
            new Company("Rashin Geothermal", 300, this, new Color(230, 23, 23)),
            new Company("Superslick Oil", 400, this, new Color(23, 230, 230)),
            new Company("Whoops Uranium", 400, this, new Color(230, 23, 230)),};
    }

    public Company getCompany(String name) {
        for (Company company : companies) {
            if (company.toString().equalsIgnoreCase(name)) {
                return company;
            }
        }
        return null;
    }

    public Company getCompany(int id) {
        if ((id >= 0) && (id < 6)) {
            return companies[id];
        }
        return null;
    }

    public boolean allCompaniesActive() {
        for (Company company : companies) {
            if (!company.getActive()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Company> inactiveCompanies() {
        ArrayList<Company> inactive = new ArrayList<>();
        for (Company company : companies) {
            if (!company.getActive()) {
                inactive.add(company);
            }
        }
        return inactive;
    }

    public ArrayList<Company> activeCompanies() {
        ArrayList<Company> active = new ArrayList<>();
        for (Company company : companies) {
            if (company.getActive()) {
                active.add(company);
            }
        }
        return active;
    }

    public Company activateCompany(Company company, Tile tile) {
        company.activate(tile);
        for (Tile t : board.getAllConnectedTiles(tile)) {
            company.addTile(t);
        }
        return company;
    }

    public int numberOfCompaniesLargerThan(int size) {
        int n = 0;
        for (Company company : companies) {
            n += (company.size() >= size ? 1 : 0);
        }
        return n;
    }
}
