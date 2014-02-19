package com.powerup.gui;

//import com.powerup.listeners.FrameListener;
import com.powerup.logic.Game;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameFrame implements Runnable {

    private final Game game;
    private JFrame frame;
    private BoardPanel boardPanel;
    private InfoPanel infoPanel;
    private TilesPanel tilesPanel;
    private CashPanel cashPanel;
    private ActionsPanel actionsPanel;
    private CommandsPanel commandsPanel;
    private Window window;
    private GridBagConstraints c;

    public GameFrame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("powerUp 0.8");
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(7, 10, 13, 10));
        c = new GridBagConstraints();
        c.insets = new Insets(5, 8, 5, 8);
        c.fill = GridBagConstraints.BOTH;
        createComponents(frame.getContentPane());
        ImageIcon icon = new ImageIcon("icons/window.png");
        frame.setIconImage(icon.getImage());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        frame.addWindowListener(new FrameListener(frame)); -- see FrameListener class JavaDoc
        window = new Window(game, this);
        game.setUp(window);
    }

    public void createComponents(Container container) {
        JComponent[] topRow = new JComponent[]{
            boardPanel = new BoardPanel(game.getBoard()),
            infoPanel = new InfoPanel()
        };
        JComponent[] bottomRow = new JComponent[]{
            tilesPanel = new TilesPanel(),
            cashPanel = new CashPanel(),
            actionsPanel = new ActionsPanel(),
            commandsPanel = new CommandsPanel()
        };
        c.gridwidth = 3;
        for (JComponent panel : topRow) {
            container.add(panel, c);
            c.gridwidth = 1;
            c.gridx = 3;
        }
        c.gridx = 0;
        c.gridy = 1;
        for (JComponent panel : bottomRow) {
            container.add(panel, c);
            c.gridx++;
        }
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
        return cashPanel;
    }

    public ActionsPanel getActionsPanel() {
        return actionsPanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
