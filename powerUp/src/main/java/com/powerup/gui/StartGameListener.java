package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartGameListener implements ActionListener {

    private JFrame frame;
    private JTextField p1Name;
    private JTextField p2Name;
    private JTextField p3Name;
    private JTextField p4Name;
    private Game game;

    public StartGameListener(JFrame frame, JTextField p1Name, JTextField p2Name, JTextField p3Name, JTextField p4Name, Game game) {
        this.frame = frame;
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p3Name = p3Name;
        this.p4Name = p4Name;
        this.game = game;
    }

    private void newGame() {
        game.getPlayer(0).setName(p1Name.getText());
        game.getPlayer(1).setName(p2Name.getText());
        game.getPlayer(2).setName(p3Name.getText());
        game.getPlayer(3).setName(p4Name.getText());
        frame.dispose();
        GameFrame ui = new GameFrame(game);
        SwingUtilities.invokeLater(ui);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(EventQueue.isDispatchThread());
        if ((p1Name.getText().equals("")) || (p2Name.getText().equals("")) || (p3Name.getText().equals("")) || (p4Name.getText().equals(""))) {
            JOptionPane.showMessageDialog(frame, "Please enter a name for all four players", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            newGame();
        }
    }
}
