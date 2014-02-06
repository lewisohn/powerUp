package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Interface implements Runnable {

    private JFrame frame;
    private final Game game;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;

    public Interface(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("powerUp");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setPreferredSize(new Dimension(790, 460));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        // Huom! Luo ensin piirtoalusta jonka lisäät container-olioon
        // Luo vasta tämän jälkeen näppäimistönkuuntelija, jonka lisäät frame-oliolle
        this.infoPanel = new InfoPanel(game.getBoard());
        this.actionPanel = new ActionPanel();
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
