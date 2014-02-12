package com.powerup.gui;

import com.powerup.logic.Game;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class StartFrame implements Runnable {

    private JFrame frame;
    private final Game game;
    private StartPanel startPanel;

    public StartFrame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("powerUp 0.1");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.getRootPane().setDefaultButton(startPanel.getDefaultButton());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        this.startPanel = new StartPanel(frame, game);
        Border padding;
        padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        startPanel.setBorder(padding);
        startPanel.setVisible(true);
        container.add(startPanel, BorderLayout.CENTER);
    }

    public JFrame getFrame() {
        return frame;
    }
}
