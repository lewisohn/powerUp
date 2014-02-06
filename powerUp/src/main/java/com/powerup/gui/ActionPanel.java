package com.powerup.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionPanel extends JPanel {

    protected JButton newGame;
    protected JButton buyShares;
    protected JButton drawTiles;
    protected JButton endTurn;

    public ActionPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        addComponents();
    }

    private void addComponents() {
        newGame = new JButton("New game");
        this.add(newGame);
        buyShares = new JButton("Buy shares");
        this.add(buyShares);
        buyShares.setEnabled(false);
        drawTiles = new JButton("Draw tiles");
        this.add(drawTiles);
        drawTiles.setEnabled(false);
        endTurn = new JButton("End turn");
        this.add(endTurn);
        endTurn.setEnabled(false);
    }

}
