package com.spakborhills.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

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

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public void switchPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        mainPanel.revalidate();
        mainPanel.repaint();
        System.out.println("Switching to " + panelName);

        if (panelName.equals("game")) {
            SwingUtilities.invokeLater(() -> {
                Component comp = mainPanel.getComponent(1);
                if (comp instanceof GamePanel) {
                    comp.requestFocusInWindow();
                }
            });
        }

    }
}
