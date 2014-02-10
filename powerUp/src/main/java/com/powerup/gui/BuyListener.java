package com.powerup.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class BuyListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        JButton source = (JButton) e.getSource();
        System.out.println(source.getY());

    }
}
