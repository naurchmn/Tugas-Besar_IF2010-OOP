package com.spakborhills.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HomePanel extends JPanel{

    private BufferedImage background;

    public HomePanel(MainFrame mainFrame) {
        try{
            background = ImageIO.read(getClass().getResource("/assets/backgrounds/home bg.png"));
        } catch (IOException e) {
            setBackground(Color.black);
        }

        this.setPreferredSize(new Dimension(512, 512));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        // bikin 4 button home screen

        JButton playButton = new GameButton("Play");
        playButton.setBounds(150, 256, 250, 30);
        this.add(playButton);

        JButton helpButton = new GameButton("Help");
        helpButton.setBounds(150, 291, 250, 30);
        this.add(helpButton);

        JButton creditButton = new GameButton("Credit");
        creditButton.setBounds(150, 326, 250, 30);
        this.add(creditButton);

        JButton exitButton = new GameButton("Exit");
        exitButton.setBounds(150, 361, 250, 30);
        this.add(exitButton);

        //action 4 button home screen
        playButton.addActionListener(e -> mainFrame.switchPanel("game"));
        helpButton.addActionListener(e -> System.out.println("Help")); //belum implement
        creditButton.addActionListener(e -> System.out.println("Credit")); //belum implement
        exitButton.addActionListener(e -> System.exit(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            g.drawImage(background, 0, 0, null);
        }
    }
}