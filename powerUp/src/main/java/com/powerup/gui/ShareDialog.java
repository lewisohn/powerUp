package com.powerup.gui;

import com.powerup.logic.Game;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class ShareDialog implements Runnable {

    public JDialog dialog;
    private final Game game;
    private SharePanel sharePanel;

    public ShareDialog(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.dialog = new JDialog(game.getWindow().getFrame(), "Buy shares", JDialog.ModalityType.DOCUMENT_MODAL);
        createComponents();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setLocation(dialog.getBounds().x, dialog.getBounds().y - 30);
        dialog.setVisible(true);
    }

    public void createComponents() {
        sharePanel = new SharePanel(game, dialog);
        dialog.add(sharePanel);
    }

    public SharePanel getSharePanel() {
        return sharePanel;
    }
}
