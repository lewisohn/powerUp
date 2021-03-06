package com.powerup.listeners;

import com.powerup.gui.help.HelpFrame;
import com.powerup.logic.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class StartListener implements ActionListener {

    private final JButton help;
    private final JButton newGame;
    private final JCheckBox doe;
    private final JFrame frame;
    private final JTextField[] names;
    private final JRadioButton[] radioButtons;
    private final Game game;
    private final HelpFrame helpFrame;
    private int numberOfPlayers;

    public StartListener(JFrame frame, HelpFrame helpFrame, Game game, JTextField[] names,
            JRadioButton[] radioButtons, JCheckBox doe, JButton newGame,
            JButton help) {
        this.frame = frame;
        this.helpFrame = helpFrame;
        this.game = game;
        this.names = names;
        this.radioButtons = radioButtons;
        this.doe = doe;
        this.newGame = newGame;
        this.help = help;
        this.numberOfPlayers = 4;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            newGameCheck();
        } else if (e.getSource() == help) {
            launchHelp();
        } else {
            for (int i = 1; i < 4; i++) {
                if (e.getSource() == radioButtons[i]) {
                    for (int j = 1; j < i + 1; j++) {
                        names[j].setEnabled(true);
                    }
                    for (int j = i + 1; j < 4; j++) {
                        names[j].setEnabled(false);
                    }
                }
            }
        }
    }

    private void newGameCheck() {
        for (int i = 1; i < 4; i++) {
            if (radioButtons[i].isSelected()) {
                numberOfPlayers = i + 1;
            }
        }
        String error = "";
        nameCheck:
        for (int i = 0; i < numberOfPlayers; i++) {
            if (names[i].getText().equals("")) {
                error = "Player " + (i + 1) + " does not have a name";
                break;
            } else if (names[i].getText().length() > 20) {
                error = "Player " + (i + 1) + "'s name is too long";
                break;
            } else if ((doe.isSelected()) && (names[i].getText().equalsIgnoreCase("The DoE")
                    || names[i].getText().equalsIgnoreCase("DoE")
                    || names[i].getText().equalsIgnoreCase("The Department of Energy"))
                    || names[i].getText().equalsIgnoreCase("Department of Energy")) {
                error = "Player " + (i + 1) + " has a misleading name: "
                        + names[i].getText();
                break;
            } else {
                for (int j = 1; j <= i; j++) {
                    if (names[i].getText().equalsIgnoreCase(names[j - 1].getText())) {
                        error = "Player " + j + " has the same name as player " + (i + 1);
                        break nameCheck;
                    }
                }
            }
        }
        if (!error.equals("")) {
            JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] playerNames = new String[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                playerNames[i] = names[i].getText();
            }
            game.launch(numberOfPlayers, playerNames, doe.isSelected());
            frame.dispose();
        }
    }

    private void launchHelp() {
        SwingUtilities.invokeLater(helpFrame);
    }
}
