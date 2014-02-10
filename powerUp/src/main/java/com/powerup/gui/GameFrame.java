package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.*;
import javax.swing.*;

public class GameFrame implements Runnable {

    private JFrame frame;
    private final Game game;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;

    public GameFrame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("powerUp 0.1");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setPreferredSize(new Dimension(790, 460));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        // Huom! Luo ensin piirtoalusta jonka lisäät container-olioon
        // Luo vasta tämän jälkeen näppäimistönkuuntelija, jonka lisäät frame-oliolle
        this.infoPanel = new InfoPanel(game.getBoard());
        this.actionPanel = new ActionPanel(frame, game);
        container.add(infoPanel);
        container.add(actionPanel);
    }

    public InfoPanel getInfoPanel() {
        return this.infoPanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
