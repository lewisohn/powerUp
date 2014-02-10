package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class InfoPanel extends JPanel {

    private final Game game;
    private JTextPane info;
    private ArrayList<String> init;
    private StyledDocument doc;

    public InfoPanel(Game game) {
        this.game = game;
        addComponents();
    }

    private void addComponents() {
        info = new JTextPane();
        info.setEditable(false);
        info.setPreferredSize(new Dimension(363, 333));
        info.setOpaque(false);
        info.setFont(Font.decode(Font.MONOSPACED));
        doc = info.getStyledDocument();
        initialText();
        JScrollPane scroll = new JScrollPane(info);
        scroll.setBorder(null);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);
    }

    private void initialText() {
        doc.addStyle(TOOL_TIP_TEXT_KEY, null);
        init = new ArrayList<>();
        init.add("Starting a new game of powerUp");
        init.add("---");
        for (int i = 1; i < 5; i++) {
            init.add("Player " + i + " is called " + game.getPlayer(i - 1));
        }
        init.add("---");
        for (int i = 0; i < init.size(); i++) {
            write(init.get(i));
        }
    }

    public void write(String line) {
        try {
            doc.insertString(doc.getLength(), line + "\n", doc.getStyle(line));
        } catch (BadLocationException ex) {
            Logger.getLogger(InfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeln(String line) {
        write(line);
        write("---");
    }
}
