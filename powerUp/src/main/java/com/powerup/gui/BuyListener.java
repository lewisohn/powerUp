package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class BuyListener implements ActionListener {

    private Game game;

    public BuyListener(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.getTurn().buyShare(((JButton) e.getSource()).getY());
    }
}
