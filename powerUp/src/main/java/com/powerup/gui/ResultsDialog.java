package com.powerup.gui;

import com.powerup.logic.Game;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class ResultsDialog implements Runnable {

    public JDialog dialog;
    private final Game game;
    private ResultsPanel resultsPanel;

    public ResultsDialog(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.dialog = new JDialog(game.getWindow().getFrame(), "Results", JDialog.ModalityType.DOCUMENT_MODAL);
        createComponents();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        dialog.setPreferredSize(new Dimension(250, 200));
//        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setLocation(dialog.getBounds().x, dialog.getBounds().y - 30);
        dialog.setVisible(true);

    }

    public void createComponents() {
        resultsPanel = new ResultsPanel(game, dialog);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(resultsPanel);
    }

    public ResultsPanel getResultsPanel() {
        return resultsPanel;
    }
}
