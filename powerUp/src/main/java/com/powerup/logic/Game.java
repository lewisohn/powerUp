package com.powerup.logic;

import com.powerup.gui.GameFrame;
import com.powerup.gui.StartFrame;
import com.powerup.gui.Window;
import java.util.Arrays;
import javax.swing.SwingUtilities;

/**
 * Primary logic class; everything is set up and accessed from here.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Game {

    private final Board board;
    private final Market market;
    private Player[] players;
    private DoE doe;
    private Turn turn;
    private Window window;

    /**
     * Sets up the board and market.
     */
    public Game() {
        board = new Board(this);
        market = new Market(this);
    }

    private void createPlayers(int n, String[] playerNames) {
        players = new Player[n];
        for (int i = 0; i < n; i++) {
            players[i] = new Player(playerNames[i]);
        }
    }

    /**
     * Returns a player based on the given ID. <p /> The IDs run from 0 to 3,
     * with 0 being the player who had the first turn.
     *
     * @param i The ID of the player to be fetched
     * @return The player in question.
     */
    public Player getPlayer(int i) {
        if ((i >= 0) && (i < players.length)) {
            return players[i];
        } else {
            return null;
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Market getMarket() {
        return market;
    }

    public Window getWindow() {
        return window;
    }

    public DoE getDoe() {
        return doe;
    }

    /**
     * Launches the graphical user interface with the "start" frame.
     */
    public void start() {
        StartFrame sFrame = new StartFrame(this);
        SwingUtilities.invokeLater(sFrame);
    }

    /**
     * Launches the graphical user interface with the "start" frame. <p /> Also
     * fills in the four player names to match the previous game.
     *
     * @param players
     */
    public void start(Player[] players) {
        StartFrame sFrame = new StartFrame(this, players);
        SwingUtilities.invokeLater(sFrame);
    }

    /**
     * Invokes the primary game frame.
     *
     * @param numberOfPlayers The number of players in the game.
     * @param playerNames A list of the names for the players.
     * @param energyDept True if the DoE was selected, false otherwise.
     */
    public void launch(int numberOfPlayers, String[] playerNames, boolean energyDept) {
        createPlayers(numberOfPlayers, playerNames);
        if (energyDept) {
            doe = new DoE("The DoE");
        }
        GameFrame ui = new GameFrame(this);
        SwingUtilities.invokeLater(ui);
    }

    /**
     * Runs some setup methods and begins the first turn. <p /> This cannot be
     * combined with the launch method because the GameFrame is invoked later by
     * SwingUtilities, and we must wait for it to load.
     *
     * @param window The primary game window.
     */
    public void setUp(Window window) {
        this.window = window;
        determineStartOrder();
        distributeInitialTiles();
        newTurn(0);
    }

    private void determineStartOrder() {
        window.writepn("Determining starting order");
        int i = 0;
        while (i < players.length) {
            Tile t = board.getRandomUnassignedTile();
            boolean hasNeighbour = false;
            for (int j = 1; j <= i; j++) {
                hasNeighbour = (t.isNextTo(players[j - 1].getTile(0)) ? true : hasNeighbour);
            }
            if (!hasNeighbour) {
                board.giveTileToPlayer(players[i], t);
                i++;
            }
        }
        Arrays.sort(players);
    }

    private void distributeInitialTiles() {
        for (int k = 0; k < players.length; k++) {
            Tile t = players[k].returnTileFromHand(0);
            board.playTileToBoard(t);
            window.write(players[k] + " drew " + t + " and will go " + ordinal(k + 1));
            for (int m = 0; m < 5; m++) {
                board.giveTileToPlayer(players[k]);
            }
        }
    }

    /**
     * Starts a new turn of the game.
     *
     * @param n The ID of the player whose turn it is.
     */
    public void newTurn(int n) {
        turn = new Turn(this, n);
    }

    public Turn getTurn() {
        return turn;
    }

    private String ordinal(int i) {
        String[] ordinals = new String[]{"", "first", "second", "third", "fourth"};
        if ((i >= 1) && (i < 5)) {
            return ordinals[i];
        } else {
            return null;
        }
    }

    /**
     * Ends the game. <p /> Re-sorts the list of players by their total cash and
     * shows the results dialog, where they are revealed in order from least
     * cash to most.
     */
    public void end() {
        Arrays.sort(players);
        window.showResultsDialog();
    }
}
