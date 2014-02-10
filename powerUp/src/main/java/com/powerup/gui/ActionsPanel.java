package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.Dimension;
import javax.swing.*;

public class ActionsPanel extends JPanel {

    private Game game;
    private JFrame frame;
    protected JButton newGame;
    protected JButton buyShares;
    protected JButton drawTiles;
    protected JButton endTurn;

    public ActionsPanel(JFrame frame, Game game) {
        this.frame = frame;
        this.game = game;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        addComponents();
    }

    private void addComponents() {
        Dimension sgl = new Dimension(4, 0);
        Dimension dbl = new Dimension(8, 0);
        this.add(Box.createRigidArea(dbl));
        newGame = new JButton("New game");
        NewGameListener newGameListener = new NewGameListener(frame, game);
        newGame.addActionListener(newGameListener);
        this.add(newGame);
        this.add(Box.createRigidArea(new Dimension(sgl)));
        buyShares = new JButton("Buy shares");
        this.add(buyShares);
        this.add(Box.createRigidArea(new Dimension(sgl)));
        buyShares.setEnabled(false);
        drawTiles = new JButton("Draw tiles");
        this.add(drawTiles);
        this.add(Box.createRigidArea(new Dimension(sgl)));
        drawTiles.setEnabled(false);
        endTurn = new JButton("End turn");
        this.add(endTurn);
        endTurn.setEnabled(false);
        this.add(Box.createRigidArea(dbl));
    }
}
