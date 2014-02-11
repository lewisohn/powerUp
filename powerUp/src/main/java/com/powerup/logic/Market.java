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
            new Company("Eclipse Solar", 200, this, Color.YELLOW),
            new Company("Maniac Timber", 200, this, Color.GREEN),
            new Company("Cortex Power", 300, this, Color.BLUE),
            new Company("Rashin Geothermal", 300, this, Color.RED),
            new Company("Superslick Oil", 400, this, Color.CYAN),
            new Company("Whoops Uranium", 400, this, Color.MAGENTA),};
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

    public Company activateCompany(String name, Tile tile) {
        Company company = getCompany(name);
        company.activate(tile);
        for (Tile t : board.getAllConnectedTiles(tile)) {
            company.addTile(t);
        }
        return company;
    }
}
