package com.spakborhills.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HomeScreen extends JPanel {

    private BufferedImage background;

    public HomeScreen(MainFrame mainFrame) {
        try {
            background = ImageIO.read(getClass().getResource("/assets/backgrounds/home bg.png"));
        } catch (IOException e) {
            setBackground(Color.BLACK);
        }

        this.setPreferredSize(new Dimension(512, 512));
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        // Judul Game
        JLabel title = new JLabel("Spakbor Hills");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBounds(120, 100, 300, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(title);

        // Tombol Play
        JButton playButton = new GameButton("Play");
        playButton.setBounds(150, 200, 250, 40);
        this.add(playButton);

        // Tombol Help
        JButton helpButton = new GameButton("Help");
        helpButton.setBounds(150, 250, 250, 40);
        this.add(helpButton);

        // Tombol Credits
        JButton creditButton = new GameButton("Credits");
        creditButton.setBounds(150, 300, 250, 40);
        this.add(creditButton);

        // Tombol Exit
        JButton exitButton = new GameButton("Exit");
        exitButton.setBounds(150, 350, 250, 40);
        this.add(exitButton);

        // Aksi Tombol
        playButton.addActionListener(e -> mainFrame.switchPanel("game"));
        helpButton.addActionListener(e -> mainFrame.switchPanel("help"));
        creditButton.addActionListener(e -> mainFrame.switchPanel("credit"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, null);
        }
    }
}
