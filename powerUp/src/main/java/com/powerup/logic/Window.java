package com.powerup.logic;

import com.powerup.gui.GameFrame;
import com.powerup.listeners.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Window {

    private Game game;
    private GameFrame gFrame;
    private TileListener tileListener;
    private CommandListener commandListener;

    public Window(Game game, GameFrame gFrame) {
        this.game = game;
        this.gFrame = gFrame;
        commandListener = new CommandListener(game, this);
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
        gFrame.getInfoPanel().writeln(string);
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
        tileListener = new TileListener(game);
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
}
