package com.powerup.gui;

import com.powerup.gui.help.HelpFrame;
import com.powerup.listeners.StartListener;
import com.powerup.logic.Game;
import com.powerup.logic.Player;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class StartPanel extends JPanel {

    private final JFrame frame;
    private final Game game;
    private final Player[] players;
    private final JTextField[] names;
    private final JRadioButton[] radioButtons;
    private final FocusListener selectListener = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            ((JTextField) e.getSource()).selectAll();
        }

        @Override
        public void focusLost(FocusEvent e) {
        }
    };
    private JButton newGame;
    private JButton help;
    private JCheckBox doe;
    private GridBagConstraints c;
    private StartListener startListener;
    private HelpFrame helpFrame;

    public StartPanel(JFrame frame, Game game, Player[] players) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        this.frame = frame;
        this.game = game;
        this.players = players;
        this.names = new JTextField[4];
        this.radioButtons = new JRadioButton[4];
        doe = new JCheckBox("Department of Energy (DoE)");
        newGame = new JButton("New game");
        help = new JButton("Help");
        helpFrame = new HelpFrame();
        startListener = new StartListener(frame, helpFrame, game, names, radioButtons,
                doe, newGame, help);
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        createComponents();
    }

    protected JButton getDefaultButton() {
        return newGame;
    }

    private void createComponents() {
        ImageIcon logo = new ImageIcon("icons/logo.png");
        JLabel title = new JLabel("powerUp 0.8", logo, JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        JLabel description = new JLabel("by Oliver Lewisohn");
        JLabel pleaseName = new JLabel("Please select and name up to four players");
        newGame.addActionListener(startListener);
        help.addActionListener(startListener);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newGame);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPanel.add(help);

        c.insets = new Insets(10, 0, 10, 0);
        c.gridwidth = 2;
        c.gridy = c.gridx = 0;

        this.add(title, c);

        c.insets = new Insets(0, 0, 10, 0);
        c.gridy++;
        this.add(description, c);

        c.gridy++;
        this.add(pleaseName, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;
        c.gridy++;
        addPlayerChoices();

        c.insets = new Insets(10, 0, 0, 0);
        c.gridwidth = 2;
        this.add(doe, c);

        c.gridy++;
        this.add(buttonPanel, c);
    }

    private void addPlayerChoices() {
        ButtonGroup radioGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new JRadioButton(String.valueOf(i + 1));
            radioButtons[i].setEnabled((i == 0 ? false : true));
            radioButtons[i].addActionListener(startListener);
            radioGroup.add(radioButtons[i]);
            this.add(radioButtons[i], c);
            c.gridx++;
            if (players.length > i && players[i] != null) {
                names[i] = new JTextField(players[i].toString(), 16);
                names[i].setEnabled(true);
            } else {
                names[i] = new JTextField("Player " + (i + 1), 16);
                names[i].setEnabled(i < players.length ? true : false);
            }
            names[i].addFocusListener(selectListener);
            this.add(names[i], c);
            c.gridx--;
            c.gridy++;
        }
        radioButtons[players.length - 1].setSelected(true);
    }
}
