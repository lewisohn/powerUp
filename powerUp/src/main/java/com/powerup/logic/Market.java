package com.powerup.logic;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The game's stock market, responsible for creating, modifying and accessing companies.
 *
 * @author Oliver Lewisohn
 * @since 2014-02-13
 */
public final class Market {

    private Company[] companies;
    private final Board board;

    /**
     * Sets up the game's six companies.
     *
     * @param board The game's board.
     */
    public Market(Board board) {
        this.board = board;
        createCompanies();
    }

    private void createCompanies() {
        companies = new Company[]{
            new Company("Eclipse Solar", 200, new Color(230, 230, 23)),
            new Company("Maniac Timber", 200, new Color(23, 230, 23)),
            new Company("Cortex Power", 300, new Color(46, 46, 230)),
            new Company("Rashin Geothermal", 300, new Color(230, 23, 23)),
            new Company("Superslick Oil", 400, new Color(23, 230, 230)),
            new Company("Whoops Uranium", 400, new Color(230, 23, 230)),};
    }

    /**
     * Fetches a company by name.
     * <p />
     * Used to respond to dialog events which return strings.
     *
     * @param name The name of the company to be fetched.
     * @return The desired company.
     */
    public Company getCompany(String name) {
        for (Company company : companies) {
            if (company.toString().equalsIgnoreCase(name)) {
                return company;
            }
        }
        return null;
    }

    /**
     * Fetches a company by ID.
     * <p />
     * IDs run from 0 (Eclipse Solar) to 5 (Whoops Uranium).
     *
     * @param id The ID of the company to be fetched.
     * @return The desired company.
     */
    public Company getCompany(int id) {
        if ((id >= 0) && (id < 6)) {
            return companies[id];
        }
        return null;
    }

    /**
     * Checks if all companies are already active.
     *
     * @return True if all companies are active, otherwise false.
     */
    public boolean allCompaniesActive() {
        for (Company company : companies) {
            if (!company.getActive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets a list of currently inactive companies.
     *
     * @return A list of currently inactive companies.
     */
    public ArrayList<Company> inactiveCompanies() {
        ArrayList<Company> inactive = new ArrayList<>();
        for (Company company : companies) {
            if (!company.getActive()) {
                inactive.add(company);
            }
        }
        return inactive;
    }

    /**
     * Gets a list of currently active companies.
     *
     * @return A list of currently active companies.
     */
    public ArrayList<Company> activeCompanies() {
        ArrayList<Company> active = new ArrayList<>();
        for (Company company : companies) {
            if (company.getActive()) {
                active.add(company);
            }
        }
        return active;
    }

    /**
     * Activates a company and assigns it some tiles.
     *
     * @param company The company to be activated.
     * @param tile The company's new headquarters.
     * @return The company which has been activated.
     */
    public Company activateCompany(Company company, Tile tile) {
        company.activate(tile);
        for (Tile t : board.getAllConnectedTiles(tile)) {
            company.addTile(t);
        }
        return company;
    }

    /**
     * Finds out how many companies are at least the given size.
     *
     * @param size The size threshold.
     * @return The number of companies at least as big as the threshold.
     */
    public int numberOfCompaniesLargerThan(int size) {
        int n = 0;
        for (Company company : companies) {
            n += (company.size() >= size ? 1 : 0);
        }
        return n;
    }
}
