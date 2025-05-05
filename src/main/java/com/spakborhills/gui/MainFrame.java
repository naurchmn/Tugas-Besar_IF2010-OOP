package com.spakborhills.gui;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Spakbor Hills");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        setContentPane(new HomePanel());
        pack();
        setLocationRelativeTo(null);
    }
}
