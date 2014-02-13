package com.powerup.gui;

import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CashPanel extends JPanel {

    private JLabel cash;

    public CashPanel() {
        addComponents();
    }

    private void addComponents() {
        cash = new JLabel("");
        cash.setFont(new Font(cash.getFont().getName(), Font.PLAIN, 16));
        cash.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(cash);
    }

    public void setCash(int amount) {
        if (String.valueOf(amount).length() > 4) {
            cash.setFont(new Font(cash.getFont().getName(), Font.PLAIN, 16));
            cash.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        } else {
            cash.setFont(new Font(cash.getFont().getName(), Font.PLAIN, 20));
            cash.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        }
        cash.setText("$" + String.valueOf(amount));
    }
}
