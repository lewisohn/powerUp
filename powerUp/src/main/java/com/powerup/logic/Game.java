package com.powerup.logic;

import com.powerup.gui.GameFrame;
import com.powerup.gui.StartFrame;
import java.util.Arrays;
import javax.swing.SwingUtilities;

/**
 * Primary logic class. Everything else is set up and accessed from here.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Game {

    private final Board board;
    private Player[] players;
    private GameFrame gFrame;
    // the game's six companies and a list to fetch them from
    private Company eclipse;
    private Company maniac;
    private Company king;
    private Company guzzler;
    private Company superslick;
    private Company whoops;
    private Company[] companies;

    /**
     * Sets up the board, players and companies. <p /> In a future version, the
     * players will not be created automatically at this stage; the user will be
     * able to name the players themselves.
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
            players[i] = new Player();
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

    /**
     * Launches the graphical user interface.
     */
    public void launch() {
        StartFrame sFrame = new StartFrame(this);
        SwingUtilities.invokeLater(sFrame);
    }

    public void setUp(GameFrame gFrame) {
        this.gFrame = gFrame;
        gFrame.getInfoPanel().writeln("Determing starting order");
        int i = 0;
        while (i < 4) {
            Tile t = board.getRandomUnassignedTile();
            boolean hasNeighbour = false;
            int j = 1;
            while (j <= i) {
                if (t.isNextTo(players[j - 1].getTile(0))) {
                    hasNeighbour = true;
                }
                j++;
            }
            if (!hasNeighbour) {
                board.giveTileToPlayer(players[i], t);
                i++;
            }
        }
        Arrays.sort(players);
        for (int k = 0;
                k < 4; k++) {
            Tile t = players[k].playTile(0);
            t.setLocation(Tile.Location.BOARD);
            gFrame.getInfoPanel().write(players[k] + " drew " + t + " and will go " + ordinal(k + 1));
            for (int m = 0; m < 5; m++) {
                players[k].giveTile(board.getRandomUnassignedTile());
            }
        }
        gFrame.getInfoPanel().write("---");
        gFrame.getBoardPanel().repaint();
        Turn turn = new Turn(this, 0);
    }

    public static String ordinal(int i) {
        String[] ordinals = new String[]{"noneth", "first", "second", "third", "fourth"};
        if ((i >= 1) && (i < 5)) {
            return ordinals[i];
        } else {
            return bigOrdinal(i);
        }
    }

    public static String bigOrdinal(int i) {
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }

    public GameFrame getGameFrame() {
        return gFrame;
    }

    public boolean allCompaniesActive() {
        for (Company company : companies) {
            if (!company.getActive()) {
                return false;
            }
        }
        return true;
    }
}
