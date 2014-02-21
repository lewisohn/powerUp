package com.powerup.listeners;

import com.powerup.gui.SharePanel;
import com.powerup.logic.Company;
import com.powerup.logic.Game;
import com.powerup.logic.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class BuyListener implements ActionListener {

    private final Game game;
    private final SharePanel sharePanel;

    public BuyListener(Game game, SharePanel sharePanel) {
        this.game = game;
        this.sharePanel = sharePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int cid = -1;
        for (int i = 0; i < 6; i++) {
            if (sharePanel.getBuyButtons()[i] == ((JButton) e.getSource())) {
                cid = i;
            }
        }
        Company clicked = game.getMarket().getCompany(cid);
        Player player = game.getTurn().getActivePlayer();
        if (player.buyShare(clicked, false)) {
            game.getWindow().writepn(player + " bought one share in " + clicked);
            if (clicked.getShares().size() > 0) {
                game.getWindow().write(clicked.getShares().size() + " "
                        + clicked.toString() + " shares remained unsold");
            } else {
                game.getWindow().write("All " + clicked.toString() + " shares were sold");
            }
        }
        game.getTurn().actionTaken();
        game.getWindow().updateBuySharesDialog();
    }
}
