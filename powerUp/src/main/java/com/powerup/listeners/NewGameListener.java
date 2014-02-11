package com.powerup.listeners;

import com.powerup.logic.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class NewGameListener implements ActionListener {

    private JFrame frame;
    private Game game;

    public NewGameListener(JFrame frame, Game game) {
        this.frame = frame;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            startGame();
        }
    }

    private void startGame() {
        frame.dispose();
        new Game().launch();
    }
}
