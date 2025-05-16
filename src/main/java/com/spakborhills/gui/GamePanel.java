package com.spakborhills.gui;

import javax.swing.*;

import java.awt.*;

import javax.swing.JPanel;

import com.spakborhills.entity.Player;
import com.spakborhills.gui.Tiles.TileManager;
import com.spakborhills.items.SuperItem;

public class GamePanel extends JPanel implements Runnable{

    // Screen Setting
    final int originalTileSize = 16; // 16 x 16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // so it's 48 x 48 tile
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 12; // screen ratio 16 : 9
    public final int screenWidth = tileSize * maxScreenCol; // 576 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 120;

    TileManager tileM = new TileManager(this);;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperItem[] item = new SuperItem[10];

    public GamePanel(MainFrame mainFrame) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);

        // Create panel for the button to prevent layout issues
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new GameButton("Back to homescreen");
        backButton.setFocusable(false);
        buttonPanel.add(backButton);

        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.NORTH);

        backButton.addActionListener(e -> {
            mainFrame.switchPanel("home");
        });

        // Request focus after initialization
        SwingUtilities.invokeLater(this::requestFocusInWindow);

    }

    public void setupGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this); // passing this Game Panel ke Thread
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2); // make sure tile first before the player below, so its like a layer

        for (int i = 0; i < item.length; i++) {
            if (item[i] != null) {
                item[i].draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        g2.dispose();
    }
}
