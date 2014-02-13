package com.powerup.listeners;

import com.powerup.logic.Company;
import com.powerup.logic.Game;
import com.powerup.logic.Player;
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
        int y = ((JButton) e.getSource()).getY();
        Company clicked = game.getMarket().getCompany((y % 40) / 2);
        Player player = game.getTurn().getActivePlayer();
        if (player.buyShare(game.getMarket().getCompany((y % 40) / 2), false)) {
            game.getWindow().writepn(player + " bought one share in " + clicked);
            game.getWindow().write(clicked.getShares().size() + clicked.toString()
                    + " shares remained unsold");
        }
        game.getTurn()
                .actionTaken();
        game.getWindow()
                .updateBuySharesDialog();
    }
}
