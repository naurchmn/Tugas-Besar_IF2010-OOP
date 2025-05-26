package com.spakborhills.view.gui;

import com.spakborhills.controller.*;
import com.spakborhills.controller.KeyHandler;
import com.spakborhills.model.entity.Entity;
import com.spakborhills.model.entity.PlayerView;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends  JPanel{
    private final int oriTileSize = 16;
    private final int scale = 3;
    private final int tileSize = oriTileSize * scale;
    private final int maxScreenCol = 12;
    private final int maxScreenRow = 12;

    //Screen Setting
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Setting
    public final int maxWorldCol = 250;
    public final int maxWorldRow = 250;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    private PlayerView player;
    private Entity npc[];
    private GameLoop gameLoop;
    private KeyHandler keyH = new KeyHandler();
    private String currentMap = "farm";

    public int getTileSize() {
        return tileSize;
    }

    public PlayerView getPlayer() {
        return player;
    }

    public GamePanel(MainFrame mainFrame) {

        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);


        player = new PlayerView(this, keyH, "asep spakbor");
        npc = new Entity[7];
        this.addKeyListener(keyH);
        gameLoop = new GameLoop(60, this::update, this::repaint);

        JButton backButton = new GameButton("Back to homescreen");
        backButton.setBounds(15, 10, 157, 25);
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

        gameLoop.getGameTime().updateGameTime();

        //pindah map kalau melebihi boundary
        if (player.getWorldX() < 95 * tileSize &&
                (player.getWorldY() > 120 * tileSize && player.getWorldY() < 124 * tileSize) &&
                currentMap.equals("farm")){ //31 hardcode maxFarmCol
            currentMap = "world";
            System.out.println("Playerriu ke " + currentMap);
        }
        else if (player.getWorldX() > 217 * tileSize &&
                (player.getWorldY() > 149 * tileSize && player.getWorldY() < 153 * tileSize) &&
                currentMap.equals("world")){
            currentMap = "farm";
            System.out.println("Playerriu ke " + currentMap);
        }

        //switch map sesuai kebutuhan
        switch (currentMap){
            case "farm":
                if(!"farm".equals(tileM.getLoadedMap())){
                    tileM.setLoadedMap("farm");
                    tileM.loadMap("/assets/FarmMaps/farm_map.txt");
                    player.setWorldX(tileSize * 95);
                    player.setWorldY(tileSize * 122);
                }
                break;
            case "world":
                if(!"world".equals(tileM.getLoadedMap())){
                    tileM.setLoadedMap("world");
                    tileM.loadMap("/assets/WorldMaps/WorldMaps");
                    player.setWorldX(tileSize * 216);
                    player.setWorldY(tileSize * 151);
                }
        }
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //draw background tiles
        tileM.draw(g2);

        //draw player
        player.draw(g2);

        //draw time
        gameLoop.getGameTime().displayGameTime(g2);
    }

    public String getCurrentMap() {
        return this.currentMap;
    }
}
