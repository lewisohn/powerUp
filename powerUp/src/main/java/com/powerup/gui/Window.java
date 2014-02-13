package com.powerup.gui;

import com.powerup.listeners.CommandListener;
import com.powerup.listeners.TileListener;
import com.powerup.logic.Company;
import com.powerup.logic.Game;
import com.powerup.logic.Tile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    public void deactivateTileListener() {
        gFrame.getTilesPanel().removeMouseListener(tileListener);
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

    public Company showTakeOverDialog(Company comp1, Company comp2) {
        Object[] strings = new Object[]{comp1.toString(), comp2.toString()};
        Object result = JOptionPane.showInputDialog(gFrame.getFrame(),
                "Two companies are the same size. Select the one to survive.",
                "Select a company",
                JOptionPane.QUESTION_MESSAGE,
                null,
                strings,
                strings[0]);
        if (result == null) {
            return showTakeOverDialog(comp1, comp2);
        }
        return game.getMarket().getCompany((String) result);
    }

    public void showBuySharesDialog() {
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
                writepn("Thanks for playing!");
            }
        });
        t.setRepeats(false);
        t.start();
    }

    public void buttonCheck(int actions, int startActions, int handSize, int tilesRemaining, boolean tilePlayed) {
        if (actions <= 0) {
            disableButton(1);
            disableButton(2);
            enableButton(3);
        } else {
            if ((handSize < 5) && (tilesRemaining > 0)) {
                enableButton(2);
            } else {
                disableButton(2);
            }
            if (actions == startActions) {
                disableButton(1);
                disableButton(3);
            } else {
                if (tilePlayed) {
                    enableButton(1);
                    enableButton(3);
                } else {
                    disableButton(1);
                    disableButton(3);
                }
            }
        }
    }

}
