package com.powerup.gui;

import com.powerup.logic.Game;
import com.powerup.logic.Player;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class StartFrame implements Runnable {

    private JFrame frame;
    private final Game game;
    private StartPanel startPanel;
    private Player[] players;

    public StartFrame(Game game, Player[] players) {
        this.game = game;
        this.players = players;
    }

    public StartFrame(Game game) {
        this.game = game;
        players = new Player[4];
    }

    @Override
    public void run() {
        frame = new JFrame("powerUp 0.8");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.getRootPane().setDefaultButton(startPanel.getDefaultButton());
        ImageIcon icon = new ImageIcon("icons/window.png");
        frame.setIconImage(icon.getImage());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        this.startPanel = new StartPanel(frame, game, players);
        startPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        startPanel.setVisible(true);
        container.add(startPanel, BorderLayout.CENTER);
    }

    public JFrame getFrame() {
        return frame;
    }
}
