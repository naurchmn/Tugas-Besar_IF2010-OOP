package com.spakborhills.view.gui;

import com.spakborhills.controller.*;
import com.spakborhills.controller.KeyHandler;
import com.spakborhills.model.entity.Entity;
import com.spakborhills.model.entity.Player;
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
    private Player player;
    private PlayerView playerView;
    private PlayerController playerController;
    private Entity npc[];
    private GameLoop gameLoop;
    private KeyHandler keyH = new KeyHandler();
    private String currentMap = "farm";
    private String currentTileType;

    public int getTileSize() {
        return tileSize;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public GamePanel(MainFrame mainFrame) {

        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        player = new Player("Asep Spakbor", "male");
        playerView = new PlayerView(this, keyH, player);
        playerController = new PlayerController(player, playerView);

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

        // pindah map kalau melebihi boundary
        if (playerView.getWorldX() < 95 * tileSize &&
                (playerView.getWorldY() > 120 * tileSize && playerView.getWorldY() < 124 * tileSize) &&
                currentMap.equals("farm")){ //31 hardcode maxFarmCol
            currentMap = "world";
            System.out.println("Playerriu ke " + currentMap);
        }
        else if (playerView.getWorldX() > 217 * tileSize &&
                (playerView.getWorldY() > 149 * tileSize && playerView.getWorldY() < 153 * tileSize) &&
                currentMap.equals("world")){
            currentMap = "farm";
            System.out.println("Playerriu ke " + currentMap);
        }

        // switch map sesuai kebutuhan
        switch (currentMap){
            case "farm":
                if(!"farm".equals(tileM.getLoadedMap())){
                    tileM.setLoadedMap("farm");
                    tileM.loadMap("/assets/FarmMaps/farm_map.txt");
                    playerView.setWorldX(tileSize * 95);
                    playerView.setWorldY(tileSize * 122);
                }
                break;
            case "world":
                if(!"world".equals(tileM.getLoadedMap())){
                    tileM.setLoadedMap("world");
                    tileM.loadMap("/assets/WorldMaps/WorldMaps");
                    playerView.setWorldX(tileSize * 216);
                    playerView.setWorldY(tileSize * 151);
                }
        }
        currentTileType = playerView.getCurrentTileType();

        if (keyH.isMapPressed()) { // Jika tombol 'F' ditekan
            if (currentTileType.equals("000.png")) {
                playerController.tilling();
            }
            if (currentTileType.equals("046.png") || currentTileType.equals("047.png") ||
                    currentTileType.equals("039.png") || currentTileType.equals("040.png")) {
                playerController.visiting();
            }
            if (currentTileType.equals("005.png")) {

            }
        }

        playerView.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //draw background tiles
        tileM.draw(g2);

        //draw player
        playerView.draw(g2);

        //draw time
        gameLoop.getGameTime().displayGameTime(g2);
    }

    public String getCurrentMap() {
        return this.currentMap;
    }
}
