package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BoardPanel extends JPanel {

    private final Board board;
    private final Color foreground = new Color(140, 180, 140);
    private final Color background = new Color(220, 220, 220);

    public BoardPanel(Board board) {
        this.board = board;
        addComponents();
    }

    private void addComponents() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(Font.decode(Font.MONOSPACED));
        paintBoardTiles(g);
    }

    private void paintBoardTiles(Graphics g) {
        Color paintColor;
        for (ArrayList<Tile> list : board.getTiles()) {
            for (Tile tile : list) {
                if (tile.getLocation() == Tile.Location.BOARD) {
                    if (tile.getOwner() == null) {
                        paintTile(g, (34 * tile.getX()) + 13, (34 * tile.getY()) + 19, tile.toString(), foreground, true);
                    } else {
                        paintTile(g, (34 * tile.getX()) + 13, (34 * tile.getY()) + 19, tile.toString(), tile.getOwner().getColor(), true);
                        if (tile.equals(tile.getOwner().getHeadquarters())) {
                            paintHeadQuarters(g, (34 * tile.getX()) + 19, (34 * tile.getY()) + 25, tile.getOwner());
                        }
                    }

                } else {
                    paintColor = background;
                    paintTile(g, 34 * (tile.getX()) + 13, 34 * (tile.getY()) + 19, tile.toString(), paintColor, false);
                }
            }
        }
    }

    public void paintHeadQuarters(Graphics g, int x, int y, Company owner) {
        g.setColor(owner.getColor());
        g.fillRect(x, y, 20, 20);
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(owner.toString().replaceAll(" ", "_").toLowerCase() + ".png"));
            g.drawImage(img, x, y, this);
        } catch (IOException | IllegalArgumentException ex) {
//            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, null, ex);
            g.setColor(Color.BLACK);
            g.fillOval(x, y, 18, 18);
            g.setColor(owner.getColor());
            g.fillOval(x + 4, y + 4, 10, 10);
        }
    }

    private void paintTile(Graphics g, int x, int y, String ref, Color color, boolean raised) {
        if (color.equals(Color.DARK_GRAY)) {
            g.setColor(Color.BLACK);
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
        if (color.equals(Color.DARK_GRAY)) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }

        g.drawString(ref, x + 8, y + 21);
    }

    public void update() {
        this.repaint();
    }
}
