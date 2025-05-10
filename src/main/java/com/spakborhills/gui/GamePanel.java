package com.spakborhills.gui;

import com.spakborhills.game.GameLoop;
import com.spakborhills.entity.Player;
import com.spakborhills.input.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends  JPanel{
    private final int oriTileSize = 16;
    private final int scale = 1;
    private final int tileSize = oriTileSize * scale;

    private Player player;
    private GameLoop gameLoop;
    private KeyHandler keyH = new KeyHandler();

    public GamePanel(MainFrame mainFrame) {

        this.setBackground(Color.GREEN);
        this.setPreferredSize(new Dimension(512, 512));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        player = new Player(this, keyH, "asep spakbor");
        this.addKeyListener(keyH);
        gameLoop = new GameLoop(60, this::update, this::repaint);

        JButton backButton = new GameButton("Back to homescreen");
        backButton.setBounds(10, 10, 100, 20);
        this.add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.switchPanel("home");
            pauseGame();
            keyH.resetKeys();
        });
    }

    public void startGame() {
        gameLoop.startGame();
    }

    public void pauseGame(){
        gameLoop.pause();
    }

    public void update(){
        if(!gameLoop.isRunning()){
            return;
        }
        player.update();
        gameLoop.getGameTime().updateGameTime();
        gameLoop.getGameTime().displayGameTime(); //debug gametime
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);

        //debug totalgametime
        int totalMinutes = gameLoop.getGameTime().getTotalGameMinutes();
        g2.setColor(Color.white);
        g2.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        g2.drawString("Total minutes: " + totalMinutes, 300, 30);
    }
}
