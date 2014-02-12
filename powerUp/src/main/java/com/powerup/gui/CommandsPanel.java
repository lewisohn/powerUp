package com.powerup.gui;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CommandsPanel extends JPanel {

    protected JButton newGame;
    protected JButton buyShares;
    protected JButton drawTiles;
    protected JButton endTurn;

    public CommandsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        addComponents();
    }

    private void addComponents() {
        Dimension sgl = new Dimension(4, 0);
        Dimension dbl = new Dimension(8, 0);
        this.add(Box.createRigidArea(dbl));
        newGame = new JButton("New game");
//        NewGameListener newGameListener = new NewGameListener(frame, game);
//        newGame.addActionListener(newGameListener);
        this.add(newGame);
        this.add(Box.createRigidArea(new Dimension(sgl)));
        buyShares = new JButton("Buy shares");
        this.add(buyShares);
        this.add(Box.createRigidArea(new Dimension(sgl)));
//        buyShares.setEnabled(false);
        drawTiles = new JButton("Draw tiles");
        this.add(drawTiles);
        this.add(Box.createRigidArea(new Dimension(sgl)));
//        drawTiles.setEnabled(false);
        endTurn = new JButton("End turn");
        this.add(endTurn);
        this.add(Box.createRigidArea(dbl));
    }

    public JButton getEndTurnButton() {
        return endTurn;
    }

    public JButton getDrawTilesButton() {
        return drawTiles;
    }

    public JButton getBuySharesButton() {
        return buyShares;
    }

    public JButton[] getButtons() {
        return new JButton[]{newGame, buyShares, drawTiles, endTurn};
    }
}
