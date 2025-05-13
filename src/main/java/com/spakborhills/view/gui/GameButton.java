package com.spakborhills.view.gui;

import javax.swing.*;
import java.awt.*;

public class GameButton extends JButton {
    public GameButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(true);
        setBackground(Color.gray);
        setFont(new Font("Comic Sans", Font.PLAIN, 10));
        setForeground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}