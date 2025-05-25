package com.spakborhills.view.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // not used yet
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println("Key pressed: " + code); // Debug line

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = true;
        }
        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
    }
}
