package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {

    private Game game;
    private JLabel name;
    private JLabel cash;

    public PlayerPanel(Game game) {
        this.game = game;
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    private void addComponents() {
//        name = new JLabel("Blank");
//        name.setAlignmentX(Component.CENTER_ALIGNMENT);
//        this.add(name);
        cash = new JLabel("Blank");
        cash.setFont(new Font(cash.getFont().getName(), Font.PLAIN, 20));
        cash.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(cash);
    }

    public void setPlayer(Player p) {
//        name.setText(p.toString());
    }

    public void setCash(int amount) {
        cash.setText("$" + String.valueOf(amount));
    }
}
