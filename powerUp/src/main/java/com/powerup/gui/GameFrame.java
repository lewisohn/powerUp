package com.powerup.gui;

//import com.powerup.listeners.FrameListener;
import com.powerup.logic.Game;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameFrame implements Runnable {

    private final Game game;
    private JFrame frame;
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
        frame.setMinimumSize(new Dimension(833, 510));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(7, 10, 13, 10));
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        frame.addWindowListener(new FrameListener(frame));
        game.setUp(this);
    }

    public void createComponents(Container container) {
        boardPanel = new BoardPanel(game.getBoard());
        boardPanel.setBorder(BorderFactory.createTitledBorder("Playing board"));
        boardPanel.setPreferredSize(new Dimension(363, 369));
        infoPanel = new InfoPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        tilesPanel = new TilesPanel();
        tilesPanel.setBorder(BorderFactory.createTitledBorder("Available tiles"));
        tilesPanel.setPreferredSize(new Dimension(193, 63));
        playerPanel = new CashPanel();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Cash"));
        actionsPanel = new ActionsPanel();
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        commandsPanel = new CommandsPanel();
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

    public CashPanel getCashPanel() {
        return playerPanel;
    }

    public ActionsPanel getActionsPanel() {
        return actionsPanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
