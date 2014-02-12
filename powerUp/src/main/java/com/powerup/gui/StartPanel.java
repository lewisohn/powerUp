package com.powerup.gui;

import com.powerup.listeners.StartListener;
import com.powerup.logic.Game;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPanel extends JPanel {

    JFrame frame;
    JTextField[] names;
    JButton newGame;
    Game game;
    FocusListener selectAll = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            ((JTextField) e.getSource()).selectAll();
        }

        @Override
        public void focusLost(FocusEvent e) {
        }
    };

    public StartPanel(JFrame frame, Game game) {
        this.frame = frame;
        this.game = game;
        this.names = new JTextField[4];
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    protected JButton getDefaultButton() {
        return newGame;
    }

    private void addComponents() {
        ImageIcon logo = new ImageIcon(getClass().getResource("logo.png"));
        JLabel title = new JLabel("powerUp 0.1", logo, JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        JLabel description = new JLabel("by Oliver Lewisohn");
        description.setFont(Font.decode(Font.SANS_SERIF));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(description);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel pleaseName = new JLabel("Please name four players:");
        pleaseName.setFont(Font.decode(Font.SANS_SERIF));
        pleaseName.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(pleaseName);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        addNameFields();
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        newGame = new JButton("New game");
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.addActionListener(new StartListener(frame, names, game));
        this.add(newGame);
    }

    private void addNameFields() {
        for (int i = 0; i < 4; i++) {
            names[i] = new JTextField("Player " + (i + 1), 10);
            names[i].addFocusListener(selectAll);
            this.add(names[i]);
        }
    }
}
