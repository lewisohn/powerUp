package com.powerup.logic;

/**
 * Primary logic class. Everything else is set up and accessed from here.
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Game {

    private final Board board;
    private Player[] players;
    // the game's six companies and a list to fetch them from
    private Company eclipse;
    private Company maniac;
    private Company king;
    private Company guzzler;
    private Company superslick;
    private Company whoops;
    private Company[] companies;

    /**
     * Sets up the board, players and companies.
     * <p />
     * In a future version, the players will not be created automatically at
     * this stage; the user will be able to name the players themselves.
     */
    public Game() {
        board = new Board();
        players = new Player[4];
        companies = new Company[6];
        createPlayers();
        createCompanies();
    }

    private void createPlayers() {
        for (int i = 0; i < 4; i++) {
            players[i] = new Player("Player " + (i + 1));
        }
    }

    private void createCompanies() {
        eclipse = new Company("Eclipse Solar", 200);
        maniac = new Company("Maniac Timber", 200);
        king = new Company("King Coal", 300);
        guzzler = new Company("Guzzler Gas", 300);
        superslick = new Company("Superslick Oil", 400);
        whoops = new Company("Whoops Uranium", 400);
        companies[0] = eclipse;
        companies[1] = maniac;
        companies[2] = king;
        companies[3] = guzzler;
        companies[4] = superslick;
        companies[5] = whoops;
    }

    public Player getPlayer(int i) {
        if ((i >= 0) && (i < 4)) {
            return players[i];
        } else {
            return null;
        }
    }

    public Board getBoard() {
        return board;
    }

    public Company getCompany(String name) {
        for (int i = 0; i < 6; i++) {
            if (companies[i].getName().equalsIgnoreCase(name)) {
                return companies[i];
            }
        }
        return null;
    }

    // faster version of the above method for use in JUnit tests
    public Company getCompany(int id) {
        if ((id >= 0) && (id < 6)) {
            return companies[id];
        }
        return null;
    }
}
