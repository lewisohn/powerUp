package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;

public class TilesPanel extends JPanel {

    private Board board;
    private final Color foreground = new Color(140, 180, 140);
    private final Color background = new Color(220, 220, 220);
    private Tile[] tiles;

    public TilesPanel(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(Font.decode(Font.MONOSPACED));
        paintAvailableTiles(g);
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    private void paintAvailableTiles(Graphics g) {
        int i = 0;
        if (tiles != null) {
            while (i < 5) {
                if (tiles[i] != null) {
                    paintTile(g, 34 * i + 13, 19, tiles[i].toString(), foreground, true);
                } else {
                    paintTile(g, 34 * i + 13, 19, "", background, false);
                }
                i++;
            }
        }
    }

    private void paintTile(Graphics g, int x, int y, String ref, Color color, boolean raised) {
        if (ref.equals("")) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(Color.DARK_GRAY);
        }
        g.drawRect(x, y, 30, 30);
        g.setColor(color);
        if (raised) {
            g.fill3DRect(x + 1, y + 1, 29, 29, true);
        } else {
            g.fillRect(x + 1, y + 1, 29, 29);
        }
        g.setColor(Color.BLACK);
        g.drawString(ref, x + 8, y + 21);
    }

    public void update() {
        this.repaint();
    }
}
