package com.powerup.gui;

import com.powerup.logic.Turn;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TileListener implements MouseListener {

    private Turn turn;

    public TileListener(Turn turn) {
        this.turn = turn;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int intersection = intersects(e);
        if (intersection >= 0) {
            turn.playTile(intersection);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private int intersects(MouseEvent e) {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        if ((y >= 19) && (y <= 49)) {
            for (int i = 0; i < 5; i++) {
                if ((x >= (14 + (i * 34))) && (x <= (44 + (i * 34)))) {
                    return i;
                }
            }
        }
        return -1;
    }
}
