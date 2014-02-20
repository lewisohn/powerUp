package com.powerup.gui;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CommandsPanel extends JPanel {

    protected JButton newGame;
    protected JButton buyShares;
    protected JButton drawTiles;
    protected JButton endTurn;
    private JButton[] buttons;

    public CommandsPanel() {
        this.setBorder(BorderFactory.createTitledBorder("Commands"));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        addComponents();
    }

    private void addComponents() {
        Dimension dim = new Dimension(4, 0);
        buttons = new JButton[]{
            new JButton("New game"),
            new JButton("Stock market"),
            new JButton("Draw tiles"),
            new JButton("End turn")
        };
        this.add(Box.createRigidArea(dim));
        this.add(Box.createRigidArea(dim));
        for (JButton button : buttons) {
            this.add(button);
            this.add(Box.createRigidArea(dim));
        }
        this.add(Box.createRigidArea(dim));
    }

    public JButton[] getButtons() {
        return buttons;
    }
}
