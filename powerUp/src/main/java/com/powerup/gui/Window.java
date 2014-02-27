package com.powerup.gui;

import com.powerup.listeners.CommandListener;
import com.powerup.listeners.TileListener;
import com.powerup.logic.Company;
import com.powerup.logic.Game;
import com.powerup.logic.Player;
import com.powerup.logic.Tile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Contains all methods that update the GUI.
 *
 * @author Oliver Lewisohn
 * @since 2014-02-13
 */
public class Window {

    private final Game game;
    private final GameFrame gFrame;
    private final CommandListener commandListener;
    private final TileListener tileListener;
    private ResultsDialog resultsDialog;
    private ShareDialog shareDialog;

    /**
     * Creates a new window.
     *
     * @param game The game.
     * @param gFrame The game's primary frame.
     */
    public Window(Game game, GameFrame gFrame) {
        this.game = game;
        this.gFrame = gFrame;
        commandListener = new CommandListener(game, this);
        tileListener = new TileListener(game);
        for (JButton button : gFrame.getCommandsPanel().getButtons()) {
            button.addActionListener(commandListener);
        }
    }

    /**
     * Activates the listener that allows tiles to be played to the board.
     */
    public void activateTileListener() {
        gFrame.getTilesPanel().addMouseListener(tileListener);
        gFrame.getTilesPanel().addMouseMotionListener(tileListener);
    }

    /**
     * Deactivates the listener that allows tiles to be played to the board.
     */
    public void deactivateTileListener() {
        for (MouseListener ml : gFrame.getTilesPanel().getMouseListeners()) {
            gFrame.getTilesPanel().removeMouseListener(ml);
        }
        for (MouseMotionListener mml : gFrame.getTilesPanel().getMouseMotionListeners()) {
            gFrame.getTilesPanel().removeMouseMotionListener(mml);
        }
//        gFrame.getTilesPanel().removeMouseListener(tileListener);
//        gFrame.getTilesPanel().removeMouseMotionListener(tileListener);
    }

    /**
     * Disables all buttons (except "New game") in the command panel.
     */
    public void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            disableButton(i + 1);
        }
    }

    public JFrame getFrame() {
        return gFrame.getFrame();
    }

    /**
     * Shows the dialog that allows a player to choose which company is created.
     *
     * @return The company chosen by the player.
     */
    public Company showCreateCompanyDialog() {
        Object[] options = game.getMarket().getInactiveCompanies().toArray();
        Object[] strings = new Object[options.length];
        for (int i = 0; i < options.length; i++) {
            strings[i] = options[i].toString();
        }
        Object result = JOptionPane.showInputDialog(gFrame.getFrame(),
                "Select a company to establish.",
                "Select a company",
                JOptionPane.QUESTION_MESSAGE,
                null,
                strings,
                strings[0]);
        if (result == null) {
            return showCreateCompanyDialog();
        }
        return game.getMarket().getCompany((String) result);
    }

    /**
     * Shows the dialog that asks a player to choose the order of takeovers.
     *
     * @param companies A list of companies which can be taken over.
     * @return The company selected.
     */
    public Company showOrderDialog(ArrayList<Company> companies) {
        return showTakeoverDialog(companies, "Two or more companies are the same"
                + " size. Select the one to be taken over first.", "Select a company");
    }

    /**
     * Shows the results dialog at the end of the game.
     */
    public void showResultsDialog() {
        writepn("Here are the results...");
        resultsDialog = new ResultsDialog(game);
        Timer t = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(resultsDialog);
                Timer t2 = new Timer(1500 * (game.getPlayers().length + 1), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        writepn(game.getPlayer(0) + " won the game with assets of $"
                                + game.getPlayer(0).getCash() + "!");
                        deactivateTileListener();
                    }
                });
                t2.setRepeats(false);
                t2.start();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    /**
     * Shows the stock market dialog, where shares can be bought.
     */
    public void showStockMarketDialog() {
        shareDialog = new ShareDialog(game);
        SwingUtilities.invokeLater(shareDialog);
    }

    /**
     * Shows the dialog that asks a player which company should survive a
     * merger. <p /> This is only necessary when two or more companies are the
     * same size.
     *
     * @param companies The list of companies involved in the merger.
     * @return The company selected.
     */
    public Company showSurvivorDialog(ArrayList<Company> companies) {
        return showTakeoverDialog(companies, "Two or more companies are the same"
                + " size. Select the one to survive.", "Select a company");
    }

    /**
     * Updates everything in the game window.
     *
     * @param activePlayer The currently active player.
     * @param actions The number of actions the player has in the current turn.
     * @param startActions The number of actions the player had at the start of
     * their turn.
     * @param tilePlayed True if a tile has been played on the player's current
     * turn, otherwise false.
     */
    public void update(Player activePlayer, int actions, int startActions, boolean tilePlayed) {
        updateBoard();
        updateTiles(activePlayer.getTiles());
        setActions(actions);
        setCash(activePlayer.getCash());
        updateInfoPanel(activePlayer.toString() + "'s turn");
        buttonCheck(actions, startActions, activePlayer.getHandSize(), game.getBoard().unassignedTilesRemaining(), tilePlayed);
    }

    /**
     * Updates the board display.
     */
    public void updateBoard() {
        gFrame.getBoardPanel().update();
    }

    /**
     * Updates the information panel to show whose turn it is.
     *
     * @param string The new title of the information panel.
     */
    public void updateInfoPanel(String string) {
        gFrame.getInfoPanel().update(string);
    }

    /**
     * Writes a new line to the information panel.
     *
     * @param string The line to be written.
     */
    public void write(String string) {
        gFrame.getInfoPanel().write(string);
    }

    /**
     * Writes a new line to the information panel, followed by a blank line.
     *
     * @param string The line to be written.
     */
    public void writeln(String string) {
        gFrame.getInfoPanel().write(string);
        gFrame.getInfoPanel().write("");
    }

    /**
     * Writes a new line to the information panel, preceded by a blank line.
     *
     * @param string The line to be written.
     */
    public void writepn(String string) {
        gFrame.getInfoPanel().write("");
        gFrame.getInfoPanel().write(string);
    }

    /* Private methods: no Javadoc */
    private void buttonCheck(int actions, int startActions, int handSize,
            int tilesRemaining, boolean tilePlayed) {
        if (actions <= 0) {
            disableButton(1);
            disableButton(2);
            enableButton(3);
        } else {
            enableButton(1);
            if ((handSize < 5) && (tilesRemaining > 0)) {
                enableButton(2);
            } else {
                disableButton(2);
            }
            if (actions == startActions || !tilePlayed) {
                disableButton(3);
            } else {
                enableButton(3);
            }
        }
    }

    private void disableButton(int n) {
        if ((n >= 0) && (n < 4)) {
            gFrame.getCommandsPanel().getButtons()[n].setEnabled(false);
        }
    }

    private void enableButton(int n) {
        if ((n >= 0) && (n < 4)) {
            gFrame.getCommandsPanel().getButtons()[n].setEnabled(true);
        }
    }

    private void setActions(int n) {
        gFrame.getActionsPanel().setActions(n);
    }

    private void setCash(int n) {
        gFrame.getCashPanel().setCash(n);
    }

    private Company showTakeoverDialog(ArrayList<Company> companies, String message, String title) {
        Object[] options = new Object[companies.size()];
        for (int i = 0; i < companies.size(); i++) {
            options[i] = companies.get(i).toString();
        }
        Object result = JOptionPane.showInputDialog(gFrame.getFrame(),
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (result == null) {
            return showSurvivorDialog(companies);
        }
        return game.getMarket().getCompany((String) result);
    }

    public void updateBuySharesDialog() {
        shareDialog.getSharePanel().update();
    }

    private void updateTiles(Tile[] tiles) {
        gFrame.getTilesPanel().setTiles(tiles);
        gFrame.getTilesPanel().update();
    }

}
