package com.powerup.gui;

import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionsPanel extends JPanel {

    private JLabel actions;

    public ActionsPanel() {
        this.setBorder(BorderFactory.createTitledBorder("Action" + 
                (!System.getProperty("os.name").equals("Linux") ? "s" : "")
        ));
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
