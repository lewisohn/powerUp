package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.*;
import javax.swing.*;

public class GameFrame implements Runnable {

    private JFrame frame;
    private final Game game;
    private BoardPanel boardPanel;
    private InfoPanel infoPanel;
    private TilesPanel tilesPanel;
    private ActionsPanel actionPanel;
    private GridBagConstraints c;

    public GameFrame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("powerUp 0.1");
        frame.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
//        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(7, 10, 13, 10));
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.setUp(this);
    }

    public void createComponents(Container container) {
        boardPanel = new BoardPanel(game.getBoard());
        boardPanel.setBorder(BorderFactory.createTitledBorder("Playing board"));
        boardPanel.setPreferredSize(new Dimension(363, 369));
        infoPanel = new InfoPanel(game);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        tilesPanel = new TilesPanel(game.getBoard());
        tilesPanel.setBorder(BorderFactory.createTitledBorder("Available tiles"));
        tilesPanel.setPreferredSize(new Dimension(193, 63));
        actionPanel = new ActionsPanel(frame, game);
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        c.insets = new Insets(5, 8, 5, 8);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        container.add(boardPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        container.add(infoPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        container.add(tilesPanel, c);

        c.gridy = 1;
        c.gridx = 1;
        c.gridwidth = 2;
        container.add(actionPanel, c);
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public TilesPanel getTilesPanel() {
        return tilesPanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
