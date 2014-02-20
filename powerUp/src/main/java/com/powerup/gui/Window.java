package com.powerup.gui;

import com.powerup.listeners.CommandListener;
import com.powerup.listeners.TileListener;
import com.powerup.logic.Company;
import com.powerup.logic.Game;
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

public class Window {

    private final Game game;
    private final GameFrame gFrame;
    private final TileListener tileListener;
    private final CommandListener commandListener;
    private ShareDialog shareDialog;
    private ResultsDialog resultsDialog;

    public Window(Game game, GameFrame gFrame) {
        this.game = game;
        this.gFrame = gFrame;
        commandListener = new CommandListener(game, this);
        tileListener = new TileListener(game);
        for (JButton button : gFrame.getCommandsPanel().getButtons()) {
            button.addActionListener(commandListener);
        }
    }

    public void updateTiles(Tile[] tiles) {
        gFrame.getTilesPanel().setTiles(tiles);
        gFrame.getTilesPanel().update();
    }

    public void write(String string) {
        gFrame.getInfoPanel().write(string);
    }

    public void writeln(String string) {
        gFrame.getInfoPanel().write(string);
        gFrame.getInfoPanel().write("");
    }

    public void writepn(String string) {
        gFrame.getInfoPanel().write("");
        gFrame.getInfoPanel().write(string);
    }

    public JFrame getFrame() {
        return gFrame.getFrame();
    }

    public void disableButton(int n) {
        if ((n >= 0) && (n < 4)) {
            gFrame.getCommandsPanel().getButtons()[n].setEnabled(false);
        }
    }

    public void enableButton(int n) {
        if ((n >= 0) && (n < 4)) {
            gFrame.getCommandsPanel().getButtons()[n].setEnabled(true);
        }
    }

    public void setActions(int n) {
        gFrame.getActionsPanel().setActions(n);
    }

    public void setCash(int n) {
        gFrame.getCashPanel().setCash(n);
    }

    public void updateBoard() {
        gFrame.getBoardPanel().update();
    }

    public void activateTileListener(Game game) {
        gFrame.getTilesPanel().addMouseListener(tileListener);
        gFrame.getTilesPanel().addMouseMotionListener(tileListener);
    }

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

    public Company showCreateCompanyDialog() {
        Object[] options = game.getMarket().inactiveCompanies().toArray();
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

    public Company showSurvivorDialog(ArrayList<Company> companies) {
        return showTakeoverDialog(companies, "Two or more companies are the same"
                + " size. Select the one to survive.", "Select a company");
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

    public Company showOrderDialog(ArrayList<Company> companies) {
        return showTakeoverDialog(companies, "Two or more companies are the same"
                + " size. Select the one to be taken over first.", "Select a company");
    }

    public void showStockMarketDialog() {
        shareDialog = new ShareDialog(game);
        SwingUtilities.invokeLater(shareDialog);
    }

    public void updateBuySharesDialog() {
        shareDialog.getSharePanel().update();
    }

    public void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            disableButton(i + 1);
        }
    }

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

    public void buttonCheck(int actions, int startActions, int handSize,
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

    public void updateInfoPanel(String string) {
        gFrame.getInfoPanel().update(string);
    }
}
