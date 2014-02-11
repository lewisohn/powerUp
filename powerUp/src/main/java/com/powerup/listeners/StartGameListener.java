package com.powerup.listeners;

import com.powerup.gui.GameFrame;
import com.powerup.logic.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartGameListener implements ActionListener {

    private JFrame frame;
    private JTextField[] names;
    private Game game;

    public StartGameListener(JFrame frame, JTextField[] names, Game game) {
        this.frame = frame;
        this.names = names;
        this.game = game;
    }

    private void newGame() {
        for (int i = 0; i < 4; i++) {
            game.getPlayer(i).setName(names[i].getText());
        }
        frame.dispose();
        GameFrame ui = new GameFrame(game);
        SwingUtilities.invokeLater(ui);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String error = "";
        nameCheck:
        for (int i = 0; i < 4; i++) {
            if (names[i].getText().equals("")) {
                error = "Player " + (i + 1) + " does not have a name";
                break;
            } else if (names[i].getText().length() > 20) {
                error = "Player " + (i + 1) + "'s name is too long";
                break;
            } else {
                for (int j = 1; j <= i; j++) {
                    if (names[i].getText().equals(names[j - 1].getText())) {
                        error = "Player " + j + " has the same name as player " + (i + 1);
                        break nameCheck;
                    }
                }
            }
        }
        if (!error.equals("")) {
            JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            newGame();
        }
    }
}
