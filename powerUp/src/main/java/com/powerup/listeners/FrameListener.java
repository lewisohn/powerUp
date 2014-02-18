package com.powerup.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * Not in use at the moment. <p /> Previously created because the game window
 * was experiencing graphical problems when restored after being minimised. I
 * found the cause and fixed it, but I'm keeping this in the project in case the
 * problems reoccur.
 *
 * @author Oliver Lewisohn
 */
public class FrameListener implements WindowListener {

    private final JFrame frame;

    public FrameListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        frame.validate();
    }

    // none of the overridden methods below does anything
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
