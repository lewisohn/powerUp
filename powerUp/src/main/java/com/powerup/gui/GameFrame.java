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
    private CashPanel playerPanel;
    private ActionsPanel actionsPanel;
    private CommandsPanel commandsPanel;
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
        playerPanel = new CashPanel(game);
        playerPanel.setBorder(BorderFactory.createTitledBorder("Cash"));
        actionsPanel = new ActionsPanel(game);
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        commandsPanel = new CommandsPanel(frame, game);
        commandsPanel.setBorder(BorderFactory.createTitledBorder("Commands"));

        c.insets = new Insets(5, 8, 5, 8);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        container.add(boardPanel, c);

        c.gridx = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        container.add(infoPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        container.add(tilesPanel, c);

        c.gridx = 1;
        container.add(playerPanel, c);

        c.gridx = 2;
        container.add(actionsPanel, c);

        c.gridx = 3;
        container.add(commandsPanel, c);
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

    public CommandsPanel getCommandsPanel() {
        return commandsPanel;
    }

    public CashPanel getPlayerPanel() {
        return playerPanel;
    }

    public ActionsPanel getActionsPanel() {
        return actionsPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Company createCompany(Tile tile) {
        Object[] options = game.getMarket().inactiveCompanies().toArray();
        Object[] strings = new Object[options.length];
        for (int i = 0; i < options.length; i++) {
            strings[i] = options[i].toString();
        }

        Object result = JOptionPane.showInputDialog(frame,
                "Select a company to establish.",
                "Select a company",
                JOptionPane.QUESTION_MESSAGE,
                null,
                strings,
                strings[0]);
        if (result == null) {
            return createCompany(tile);
        }
        return game.getMarket().activateCompany((String) result, tile);
    }

    public Company takeOver(Company comp1, Company comp2) {
        Object[] strings = new Object[]{comp1.toString(), comp2.toString()};
        Object result = JOptionPane.showInputDialog(frame,
                "Two companies are the same size. Select the one to take over.",
                "Select a company",
                JOptionPane.QUESTION_MESSAGE,
                null,
                strings,
                strings[0]);
        if (result == null) {
            return takeOver(comp1, comp2);
        }
        return game.getMarket().getCompany((String) result);
    }
}
