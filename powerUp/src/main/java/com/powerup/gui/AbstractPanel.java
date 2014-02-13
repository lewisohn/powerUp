package com.powerup.gui;

import com.powerup.logic.Game;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {

    protected Game game;
    protected Window window;
    protected ActionListener doneListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            window.dispose();
        }
    };

    protected JLabel makeHeader(String text) {
        JLabel header = new JLabel(text);
        Font font = header.getFont();
        header.setFont(new Font(font.getFontName(), Font.BOLD, 12));
        return header;
    }

    protected JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }

    protected JButton makeButton(String text) {
        JButton button = new JButton(text);
        return button;
    }

    protected abstract void createComponents();

    public abstract void update();
}
