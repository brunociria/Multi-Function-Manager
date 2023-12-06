package net.ciria.graphmaster.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KeyListener extends KeyAdapter {
    private final FunctionGrapher functionGrapher;


    public KeyListener(FunctionGrapher functionGrapher) {
        this.functionGrapher = functionGrapher; 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            functionGrapher.calculateFunction();
            functionGrapher.repaint();
        }
    }
}
