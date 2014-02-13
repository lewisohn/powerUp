package com.powerup.listeners;

import com.powerup.logic.Company;
import com.powerup.logic.Game;
import com.powerup.logic.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class BuyListener implements ActionListener {

    private final Game game;

    public BuyListener(Game game) {
        this.game = game;
    }

    
    // FIX ME
    // Don't use a method that depends on the coordinates of the pressed button
    // as they change depending on the environment the program is running in.
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int y = ((JButton) e.getSource()).getY();
        System.out.println(y);
        System.out.println(Math.min(y % 41, y % 40) / 2);
        Company clicked = game.getMarket().getCompany(Math.min(y % 39, y % 40) / 2);
        Player player = game.getTurn().getActivePlayer();
        if (player.buyShare(clicked, false)) {
            game.getWindow().writepn(player + " bought one share in " + clicked);
            game.getWindow().write(clicked.getShares().size() + " "
                    + clicked.toString() + " shares remained unsold");
        }
        game.getTurn().actionTaken();
        game.getWindow().updateBuySharesDialog();
    }
}
