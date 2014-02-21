package com.powerup.logic;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The game's stock market, responsible for creating, modifying and accessing
 * companies.
 *
 * @author Oliver Lewisohn
 * @since 2014-02-13
 */
public final class Market {

    private Company[] companies;
    private final Board board;
    private final Game game;

    /**
     * Sets up the game's six companies.
     *
     * @param game The game.
     */
    public Market(Game game) {
        this.game = game;
        this.board = game.getBoard();
        createCompanies();
    }

    private void createCompanies() {
        companies = new Company[]{
            new Company("Eclipse Solar", 200, new Color(230, 230, 23)),
            new Company("Maniac Timber", 200, new Color(23, 230, 23)),
            new Company("Cortex Power", 300, new Color(46, 46, 230)),
            new Company("Rufus Geothermal", 300, new Color(230, 23, 23)),
            new Company("Superslick Oil", 400, new Color(23, 230, 230)),
            new Company("Whoops Uranium", 400, new Color(230, 23, 230)),};
    }

    /**
     * Fetches a company by name. <p /> Used to respond to dialog events which
     * return strings.
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
     * Fetches a company by ID. <p /> IDs run from 0 (Eclipse Solar) to 5
     * (Whoops Uranium).
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

    public ArrayList determineMajorAndMinor(Company company) {
        ArrayList<ArrayList<Player>> shareholders = new ArrayList<>();
        ArrayList<Player> maj = new ArrayList<>();
        ArrayList<Player> min = new ArrayList<>();
        shareholders.add(maj);
        shareholders.add(min);
        for (int i = 0; i < game.getPlayers().length; i++) {
            shareholders = shareholderCheck(shareholders, game.getPlayer(i), company);
        }
        if (game.getDoe() != null) {
            shareholders = shareholderCheck(shareholders, game.getDoe(), company);
        }
        return shareholders;
    }

    private ArrayList shareholderCheck(ArrayList<ArrayList<Player>> shareholders, Player player, Company company) {
        int shares = player.getNumberOfShares(company);
        if (shares > 0) {
            if ((shareholders.get(0).isEmpty()) || (shares > shareholders.get(0).get(0).getNumberOfShares(company))) {
                shareholders.set(1,(ArrayList<Player>) shareholders.get(0).clone());
                shareholders.get(0).clear();
                shareholders.get(0).add(player);
            } else if (shares == shareholders.get(0).get(0).getNumberOfShares(company)) {
                shareholders.get(0).add(player);
            } else {
                if ((shareholders.get(1).isEmpty()) || (shares > shareholders.get(1).get(0).getNumberOfShares(company))) {
                    shareholders.get(1).clear();
                    shareholders.get(1).add(player);
                } else if (shares == shareholders.get(1).get(0).getNumberOfShares(company)) {
                    shareholders.get(1).add(player);
                }
            }
        }
        return shareholders;
    }
}
