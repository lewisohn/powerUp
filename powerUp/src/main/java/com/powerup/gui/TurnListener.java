package com.powerup.gui;

import com.powerup.logic.Turn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurnListener implements ActionListener {

    private Turn turn;

    public TurnListener(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        turn.endTurn();
    }
}
