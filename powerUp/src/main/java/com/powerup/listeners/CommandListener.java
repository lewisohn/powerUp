package com.powerup.listeners;

import com.powerup.gui.Window;
import com.powerup.logic.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CommandListener implements ActionListener {

    private final Game game;
    private final Window window;
    private final JFrame frame;

    public CommandListener(Game game, Window window) {
        this.game = game;
        this.window = window;
        this.frame = window.getFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        switch (source.getText()) {
            case "New game":
                newGameClicked();
                break;
            case "Stock market":
                stockMarketClicked();
                break;
            case "Draw tiles":
                drawTilesClicked();
                break;
            case "End turn":
                endTurnClicked();
                break;
        }
    }

    private void newGameClicked() {
        Object[] options = {"Yes", "No"};
        int result = JOptionPane.showOptionDialog(frame,
                "Are you sure you want to start a new game?"
                + "\n The current game will be aborted.",
                "Confirmation required",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (result == JOptionPane.YES_OPTION) {
            frame.dispose();
            new Game().start(game.getPlayers());
        }
    }

    private void stockMarketClicked() {
        window.showStockMarketDialog();
    }

    private void drawTilesClicked() {
        Player p = game.getTurn().getActivePlayer();
        game.getBoard().refillPlayerHand(p);
        window.writepn(p + " drew new tiles");
        if (game.getBoard().unassignedTilesRemaining() <= 0) {
            window.write("All tiles were now assigned");
        }
        game.getTurn().actionTaken();
    }

    private void endTurnClicked() {
        game.getTurn().endTurn();
    }
}
