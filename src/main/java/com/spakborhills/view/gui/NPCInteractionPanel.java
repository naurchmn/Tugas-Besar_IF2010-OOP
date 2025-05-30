package com.spakborhills.view.gui;

import com.spakborhills.controller.PlayerController;
import com.spakborhills.model.entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPCInteractionPanel extends JPanel {
    private BufferedImage background;
    private JTextField playerNameField;
    private JTextField farmNameField;
    private JButton confirmButton;
    private JButton backButton;
    GamePanel gp;

    public NPCInteractionPanel(MainFrame mainFrame, GamePanel gp) {
        this.gp = gp;

        try {
            background = ImageIO.read(getClass().getResource("/assets/backgrounds/home bg new.png"));
        } catch (IOException e) {
            setBackground(Color.black);
        }

        this.setPreferredSize(new Dimension(576, 576));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        // bikin 4 button home screen

        JButton proposeButton = new GameButton("Proposing");
        proposeButton.setBounds(160, 240, 250, 50);
        this.add(proposeButton);

        JButton marryButton = new GameButton("Marrying");
        marryButton.setBounds(160, 295, 250, 50);
        this.add(marryButton);

        JButton chatButton = new GameButton("Chatting");
        chatButton.setBounds(160, 350, 250, 50);
        this.add(chatButton);

        JButton giftButton = new GameButton("Gifting");
        giftButton.setBounds(160, 405, 250, 50);
        this.add(giftButton);

        JButton backGameButton = new GameButton("Back to game");
        backGameButton.setBounds(15, 10, 157, 25);
        this.add(backGameButton);

        proposeButton.addActionListener(e -> {gp.getPlayerController().proposing(gp.getCurrentNPC());});
        marryButton.addActionListener(e -> {gp.getPlayerController().marrying(gp.getCurrentNPC());}); //belum implement
        chatButton.addActionListener(e -> System.out.println("Chatting with NPC " + gp.getCurrentNPC().getName())); //belum implement
        giftButton.addActionListener(e -> {gp.getPlayerController().gifting(gp.getCurrentNPC(), gp.getPlayer().getItemHeld());});
        backGameButton.addActionListener(e -> {mainFrame.switchPanel("game");});
    }
}
