package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;

public class SharePanel extends JPanel {

    private Game game;
    private GridBagConstraints c;
    private BuyListener buyListener;

    public SharePanel(Game game) {
        Font oldLabelFont = UIManager.getFont("Label.font");
        UIManager.put("Label.font", oldLabelFont.deriveFont(Font.PLAIN));
        this.game = game;
        this.buyListener = new BuyListener();
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        createComponents();
    }

    private void addHeader(String text) {
        JLabel header = new JLabel(text);
        Font font = header.getFont();
        header.setFont(new Font(font.getFontName(), Font.BOLD, 12));
        this.add(header, c);
    }

    private void createComponents() {
        c.insets = new Insets(8, 10, 8, 10);
        c.gridy = 0;
        c.gridx = 0;
        addHeader("Company");
        c.gridx++;
        addHeader("Available");
        c.gridx++;
        addHeader("Unit cost");
        c.gridx++;
        addHeader("Buy");
        for (int i = 0; i < 6; i++) {
            c.gridx = 0;
            c.gridy = i + 1;
            this.add(new JLabel(game.getCompany(i).toString()), c);
            c.gridx++;
            this.add(new JLabel(String.valueOf(game.getCompany(i).getShares().size())), c);
            c.gridx++;
            this.add(new JLabel("$" + String.valueOf(game.getCompany(i).sellPrice())), c);
            c.gridx++;
            JButton buy = new JButton("Buy");
            buy.addActionListener(buyListener);
            if (!game.getCompany(i).getActive()) {
                buy.setEnabled(false);
            }
            this.add(buy, c);
        }
    }
}
