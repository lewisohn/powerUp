package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ShareListener implements ActionListener {

    private Turn turn;

    public ShareListener(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        turn.launchShareFrame();
    }
}
