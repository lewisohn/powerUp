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
    private final Market market;
    private Player[] players = new Player[4];
    private GameFrame gFrame;
    private Turn turn;

    /**
     * Sets up the board and market.
     */
    public Game() {
        board = new Board();
        market = new Market(board);
        createPlayers();

    }

    private void createPlayers() {
        for (int i = 0; i < 4; i++) {
            players[i] = new Player();
        }
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

    public Market getMarket() {
        return market;
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
        determineStartOrder();
        playInitialTiles();
        gFrame.getBoardPanel().repaint();
        turn = new Turn(this, 0);
    }

    private void determineStartOrder() {
        gFrame.getInfoPanel().writeln("");
        gFrame.getInfoPanel().write("Determining starting order");
        int i = 0;
        while (i < 4) {
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

    private void playInitialTiles() {
        for (int k = 0; k < 4; k++) {
            Tile t = players[k].playTile(0);
            t.setLocation(Tile.Location.BOARD);
            gFrame.getInfoPanel().write(players[k] + " drew " + t + " and will go " + ordinal(k + 1));
            for (int m = 0; m < 5; m++) {
                board.giveTileToPlayer(players[k]);
            }
        }
    }

    public void newTurn() {
        turn.endTurn();
    }

    public Turn getTurn() {
        return turn;
    }

    private String ordinal(int i) {
        String[] ordinals = new String[]{"noneth", "first", "second", "third", "fourth"};
        if ((i >= 1) && (i < 5)) {
            return ordinals[i];
        } else {
            return null;
        }
    }

    public GameFrame getGameFrame() {
        return gFrame;
    }
}
