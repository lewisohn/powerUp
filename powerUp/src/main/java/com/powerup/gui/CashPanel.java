package com.powerup.gui;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CashPanel extends JPanel {

    private JLabel cash;

    public CashPanel() {
        addComponents();
    }

    private void addComponents() {
        cash = new JLabel("Blank");
        cash.setFont(new Font(cash.getFont().getName(), Font.PLAIN, 20));
        cash.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(cash);
    }

    public void setCash(int amount) {
        cash.setText("$" + String.valueOf(amount));
    }
}
