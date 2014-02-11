package com.powerup.listeners;

import com.powerup.logic.Turn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawListener implements ActionListener {

    private Turn turn;

    public DrawListener(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        turn.drawTiles();
    }
}
