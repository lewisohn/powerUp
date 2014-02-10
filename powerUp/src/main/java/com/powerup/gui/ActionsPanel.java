package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionsPanel extends JPanel {

    private Game game;
    private JLabel actions;

    public ActionsPanel(Game game) {
        this.game = game;
        addComponents();
    }

    private void addComponents() {
        actions = new JLabel("");
        actions.setFont(new Font(actions.getFont().getName(), Font.PLAIN, 20));
        actions.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(actions);
    }

    public void setActions(int n) {
        actions.setText(String.valueOf(n));
    }
}
