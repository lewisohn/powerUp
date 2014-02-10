package com.powerup.gui;

import com.powerup.logic.*;
import javax.swing.*;

public class ActionPanel extends JPanel {

    private Game game;
    private JFrame frame;
    protected JButton newGame;
    protected JButton buyShares;
    protected JButton drawTiles;
    protected JButton endTurn;

    public ActionPanel(JFrame frame, Game game) {
        this.frame = frame;
        this.game = game;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        addComponents();
    }

    private void addComponents() {
        newGame = new JButton("New game");
        NewGameListener newGameListener = new NewGameListener(frame, game);
        newGame.addActionListener(newGameListener);
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
