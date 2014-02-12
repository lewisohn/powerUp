package com.powerup.gui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyledDocument;

public class InfoPanel extends JPanel {

    private JTextPane info;
    private StyledDocument doc;

    public InfoPanel() {
        addComponents();
    }

    private void addComponents() {
        info = new JTextPane();
        info.setEditable(false);
        info.setOpaque(false);
        info.setFont(Font.decode(Font.MONOSPACED));
        doc = info.getStyledDocument();
        doc.addStyle(TOOL_TIP_TEXT_KEY, null);
        write("Welcome to powerUp");
        JScrollPane scroll = new JScrollPane(info);
        scroll.setPreferredSize(new Dimension(363, 333));
        scroll.setBorder(null);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DefaultCaret caret = (DefaultCaret) info.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.add(scroll);
    }

    public void write(String line) {
        try {
            doc.insertString(doc.getLength(), line + "\n", doc.getStyle(line));
        } catch (BadLocationException ex) {
            System.out.println(ex);
        }
    }

    public void writeln(String line) {
        if (!line.equals("")) {
            write(line);
        }
        write("");
    }
}
