package com.spakborhills.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MainFrame() {
        setTitle("Spakbor Hills");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        HomePanel homePanel = new HomePanel(this);
        GamePanel gamePanel = new GamePanel(this);

        mainPanel.add(homePanel, "home");
        mainPanel.add(gamePanel, "game");

        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void switchPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        mainPanel.revalidate();
        mainPanel.repaint();
        System.out.println("Switching to " + panelName);

        if(panelName.equals("game")) {
            GamePanel gamePanel = (GamePanel) mainPanel.getComponent(1);
            gamePanel.startGame();
            gamePanel.requestFocusInWindow();
        }

    }
}
