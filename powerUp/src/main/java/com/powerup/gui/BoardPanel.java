package com.powerup.gui;

import com.powerup.logic.Board;
import com.powerup.logic.Company;
import com.powerup.logic.Tile;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
            g.setColor(brightness(owner.getColor()) > 0.55 ? Color.BLACK : Color.WHITE);
            g.setFont(new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize()));
            g.drawString(owner.toString().substring(0, 1), x + 7, y + 14);
            g.setFont(new Font(g.getFont().getName(), Font.PLAIN, g.getFont().getSize()));
        }
    }

    private void paintTile(Graphics g, int x, int y, String ref, Color color, boolean raised) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, 30, 30);
        g.setColor(color);
        if (raised) {
            g.fill3DRect(x + 1, y + 1, 29, 29, true);
        } else {
            g.fillRect(x + 1, y + 1, 29, 29);
        }
        g.setColor(brightness(color) > 0.55 ? Color.BLACK : Color.WHITE);
        g.drawString(ref, x + 9, y + 20);
    }

    private double brightness(Color color) {
        double brightness = 0;
        double[] weights = new double[]{0.241, 0.691, 0.068};
        for (int i = 0; i < 3; i++) {
            brightness += (weights[i] * Math.pow(color.getRGBComponents(null)[i], 2));
        }
        return Math.sqrt(brightness);
    }

    public void update() {
        this.repaint();
    }
}
