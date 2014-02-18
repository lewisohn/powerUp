package com.powerup.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyledDocument;

public final class InfoPanel extends AbstractPanel {

    private JTextPane info;
    private StyledDocument doc;
    private final GridBagConstraints c;

    public InfoPanel() {
        this.setBorder(BorderFactory.createTitledBorder("Information"));
        this.setPreferredSize(new Dimension(363, 369));
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        createComponents();
    }

    @Override
    protected void createComponents() {
        info = new JTextPane();
        info.setEditable(false);
        info.setOpaque(false);
        info.setFont(Font.decode(Font.MONOSPACED));
        doc = info.getStyledDocument();
        doc.addStyle(TOOL_TIP_TEXT_KEY, null);
        write("Welcome to powerUp");
        /* This functionality will be added later!
         * write("Press F1 to read the rules at any time");
         * write("Good luck!");
         */
        JScrollPane scroll = new JScrollPane(info);
//        scroll.setPreferredSize(new Dimension(350, 350));
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DefaultCaret caret = (DefaultCaret) info.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.add(scroll, c);
    }

    public void write(String line) {
        try {
            doc.insertString(doc.getLength(), line + "\n", doc.getStyle(line));
        } catch (BadLocationException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void update() {
        this.setBorder(BorderFactory.createTitledBorder("Information"));
    }

    public void update(String string) {
        this.setBorder(BorderFactory.createTitledBorder("Information: " + string));
    }
}
