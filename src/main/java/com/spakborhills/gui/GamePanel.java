package com.spakborhills.gui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel(MainFrame mainFrame) {

        this.setBackground(Color.GREEN);
        this.setPreferredSize(new Dimension(512, 512));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        JButton backButton = new GameButton("Back to homescreen");
        backButton.setBounds(10, 10, 100, 20);
        this.add(backButton);

        backButton.addActionListener(e -> mainFrame.switchPanel("home"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
