package com.spakborhills.view.gui;

import com.spakborhills.controller.*;
import com.spakborhills.controller.KeyHandler;
import com.spakborhills.model.entity.Entity;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.model.game.PlantManager;
import com.spakborhills.model.items.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

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
    private PlantManager plantManager = new PlantManager();
    private String currentMap = "farm";
    private String currentTileType;
    private String frontTileType;

    private String previousMap = "";
    private int entranceWorldX = -1; // Mengubah menjadi WorldX/WorldY
    private int entranceWorldY = -1; // Mengubah menjadi WorldX/WorldY

    private boolean positionSetByReturn = false;

    private boolean inventoryOpened;

    public int getTileSize() {
        return tileSize;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public void setCurrentMap(String map) {
        // Jika peta saat ini BUKAN house, dan peta tujuan adalah house,
        // maka simpan peta saat ini sebagai previousMap dan posisi WorldX/Y
        if (!this.currentMap.split(" ")[0].equals("house") && map.split(" ")[0].equals("house")) {
            this.previousMap = this.currentMap;
            this.entranceWorldX = playerView.getWorldX(); // Simpan WorldX
            this.entranceWorldY = playerView.getWorldY(); // Simpan WorldY
            System.out.println("Set previousMap to: " + previousMap + " from entrance WorldX: " + entranceWorldX + ", WorldY: " + entranceWorldY);
        }
        this.currentMap = map; // ubah currentMap
    }

    public String getCurrentMap() {
        return this.currentMap;
    }

    public void setPreviousMap(String map) {
        this.previousMap = map;
    }

    public void setEntrancePosition(int x, int y) {
    }

    public void returnToPreviousMap() {
        // Debugging: Pastikan nilai-nilai ini benar sebelum digunakan
        System.out.println("Return: previousMap=" + previousMap + ", entranceWorldX=" + entranceWorldX + ", entranceWorldY=" + entranceWorldY);

        if (!previousMap.isEmpty() && entranceWorldX != -1 && entranceWorldY != -1) {
            this.currentMap = previousMap; // Mengatur currentMap ke peta sebelumnya

            // Set posisi pemain ke posisi masuk sebelumnya menggunakan WorldX/Y yang disimpan
            playerView.setWorldX(entranceWorldX);
            playerView.setWorldY(entranceWorldY);
            playerView.setWorldY(playerView.getWorldY() + tileSize);

            positionSetByReturn = true;

//            this.previousMap = "";
//            this.entranceWorldX = -1;
//            this.entranceWorldY = -1;
            System.out.println("Returned to map: " + currentMap + " at position: " + playerView.getWorldX()/tileSize + ", " + playerView.getWorldY()/tileSize);
        } else {
            System.out.println("Could not return to previous map. Data incomplete or already reset. Falling back to default farm position.");
            this.currentMap = "farm";
            playerView.setWorldX(tileSize * 95);
            playerView.setWorldY(tileSize * 122);
            // Pastikan TileManager juga di-reset jika fallback terjadi dan peta belum "farm"
            if (!"farm".equals(tileM.getLoadedMap())) {
                tileM.setLoadedMap("farm");
                tileM.loadMap("/assets/FarmMaps/farm_map.txt");
            }
            positionSetByReturn = false;
            System.out.println("Fallback: Moved to default farm position.");
        }
    }

    public GamePanel(MainFrame mainFrame, LoginPanel loginPanel) {

        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        player = new Player(loginPanel.getPlayerName(), "male", loginPanel.getFarmName());
        playerView = new PlayerView(this, keyH, player);
        playerController = new PlayerController(player, playerView, this);

        npc = new Entity[7];
        this.addKeyListener(keyH);
        gameLoop = new GameLoop(60, this::update, this::repaint);
        gameLoop.getGameTime().addObserver(plantManager);

//        JButton backButton = new GameButton("Back to homescreen");
//        backButton.setBounds(15, 10, 157, 25);
//        this.add(backButton);
//
//        backButton.addActionListener(e -> {
//            mainFrame.switchPanel("home");
//            pauseGame();
//            keyH.resetKeys();
//        });
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

        if (player.getEnergy() == -20){
            playerController.sleeping(player.getEnergy(), gameLoop.getGameTime().getInGameHours(), gameLoop.getGameTime().getInGameMinutes());
        }

        boolean wasInventoryOpened = inventoryOpened;
        if (keyH.isInventoryPressed()){
            inventoryOpened = !inventoryOpened;
            keyH.setInventoryPressed(false);
        }

        if (inventoryOpened) {
            // Print hanya saat inventory baru dibuka
            if (!wasInventoryOpened) {
                try {
                    System.out.println("Item held : " + player.getItemHeld().getName());
                } catch (NullPointerException _) {}

                if (player.getInventory().getPlayerInventory().isEmpty()) {
                    System.out.println("Inventory empty:(");
                    System.out.println("Press I to continue game");
                } else {
                    System.out.println("Inventory :");
                    for (Map.Entry<Item, Integer> entry : player.getInventory().getPlayerInventory().entrySet()) {
                        System.out.println(entry.getKey().getName() + ": " + entry.getValue());
                    }
                    playerController.chooseItem();
                    // Setelah chooseItem selesai, langsung lanjutkan game
                    inventoryOpened = false;
                    gameLoop.getGameTime().setStartTime(System.nanoTime());
                    System.out.println("Game continued");
                }
            }
            return;
        }
        gameLoop.getGameTime().updateGameTime();
        playerView.update();

        String oldMap = currentMap;
        // pindah map kalau melebihi boundary
        if (playerView.getWorldX() < 95 * tileSize &&
                (playerView.getWorldY() > 120 * tileSize && playerView.getWorldY() < 124 * tileSize) &&
                currentMap.equals("farm")){ //31 hardcode maxFarmCol
            currentMap = "world";
            System.out.println("Playerriu ke " + currentMap);
            positionSetByReturn = false;

            if (!oldMap.split(" ")[0].equals("house")) { // Jika peta sebelumnya bukan house
                this.previousMap = "";
                this.entranceWorldX = -1;
                this.entranceWorldY = -1;
            }
        }
        else if (playerView.getWorldX() > 217 * tileSize &&
                (playerView.getWorldY() > 149 * tileSize && playerView.getWorldY() < 153 * tileSize) &&
                currentMap.equals("world")){
            currentMap = "farm";
            System.out.println("Player pindah ke " + currentMap);
            positionSetByReturn = false;

            if ((!(oldMap.split(" ")[0]).equals("house"))) { // Jika peta sebelumnya bukan house
                this.previousMap = "";
                this.entranceWorldX = -1;
                this.entranceWorldY = -1;
            }
        }

        currentTileType = playerView.getCurrentTileType();
        frontTileType = playerView.getFrontTileType();

        if (keyH.isEnterPressed()) {
            if (currentTileType != null) {
                if (currentTileType.equals("046.png") || currentTileType.equals("047.png") ||
                        currentTileType.equals("039.png") || currentTileType.equals("040.png")) {
                    System.out.println("Got in the house");
                    playerController.getInTheHouse();
                } else if (currentTileType.equals("138.png") || currentTileType.equals("139.png")) {
                    System.out.println("Got out the house");
                    playerController.getOutTheHouse();
                } else if (currentTileType.equals("000.png")) {
                    if (currentMap.equals("farm")) {
                        if (playerController.rightTool("Hoe")) {
                            playerController.tilling();
                        }
                    }
                } else if (frontTileType.equals("006.png") && player.energySufficient(5)) {
                    if (playerController.rightTool("Fishing Rod")) {
                        playerController.fishing();
                        gameLoop.getGameTime().setStartTime(System.nanoTime());
                    }
                } else if (currentTileType.equals("004.png") || currentTileType.equals("147.png")) {
                    if (playerController.holdingSeed()) {
                        playerController.planting();
                    } else if (playerController.rightTool("Hoe")) {
                        playerController.recoverLand();
                    }
                } else if (currentTileType.equals("146.png") || currentTileType.equals("148.png")) {
                    if (player.getItemHeld() == null){
                        playerController.harvesting();
                    }
                }
            }
            keyH.setEnterPressed(false);
        }

        if (keyH.isSpacePressed()) {
            if (currentTileType.equals("004.png") || currentTileType.equals("148.png")) {
                if (playerController.rightTool("Watering Can")){
                    playerController.watering();
                }
            }
            keyH.setSpacePressed(false);
        }

        // switch map sesuai kebutuhan
        String prevLoadedMap = tileM.getLoadedMap(); // Simpan map yang sudah dimuat sebelumnya
        String targetMap = currentMap; // Simpan currentMap sebelum diubah oleh tileM.setLoadedMap()

        switch (targetMap.split(" ")[0]){
            case "farm":
                if(!"farm".equals(prevLoadedMap)){ // Hanya muat ulang jika belum dimuat
                    tileM.setLoadedMap("farm");
                    tileM.loadMap("/assets/FarmMaps/farm_map.txt");
                    // Posisi default jika tidak ada posisi masuk dari rumah
                    if (entranceWorldX == -1) { // Hanya set default jika tidak ada posisi masuk yang disimpan
                        playerView.setWorldX(tileSize * 95);
                        playerView.setWorldY(tileSize * 122);
                    }
                    positionSetByReturn = false;
                }
                break;
            case "world":
                if(!"world".equals(prevLoadedMap)){ // Hanya muat ulang jika belum dimuat
                    tileM.setLoadedMap("world");
                    tileM.loadMap("/assets/WorldMaps/WorldMaps");
                    // Posisi default jika tidak ada posisi masuk dari rumah
                    if (entranceWorldX == -1) { // Hanya set default jika tidak ada posisi masuk yang disimpan
                        playerView.setWorldX(tileSize * 216);
                        playerView.setWorldY(tileSize * 151);
                    }
                    positionSetByReturn = false;
                }
                break;
            case "house" :
                if(!"house".equals(prevLoadedMap.split(" ")[0])){
                    tileM.setLoadedMap("house");
                    tileM.loadMap("/assets/HouseMap/HouseInteriorMap");
                    playerView.setWorldX(tileSize * 120);
                    playerView.setWorldY(tileSize * 122);
                    positionSetByReturn = false;
                }
                break;
        }
    }

    // Metode baru untuk menampilkan gambar
    public void showTemporaryPopUp(String imagePath, int durationMillis) {
        JWindow popup = new JWindow(SwingUtilities.getWindowAncestor(this));

        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                System.err.println("Error: Image not found at " + imagePath);
                return;
            }
            BufferedImage image = ImageIO.read(imageUrl);
            JLabel label = new JLabel(new ImageIcon(image));
            label.setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding untuk gambar

            popup.add(label, BorderLayout.CENTER);
            popup.pack();

            int x = this.getLocationOnScreen().x + (this.getWidth() - popup.getWidth()) / 2;
            int y = (this.getLocationOnScreen().y + (this.getHeight() - popup.getHeight()) / 2) - 200;
            popup.setLocation(x, y);

            popup.setVisible(true);

            Timer timer = new Timer(durationMillis, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popup.dispose();
                    ((Timer)e.getSource()).stop();
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception e) {
            System.err.println("Failed to load or display image: " + imagePath);
            e.printStackTrace();
        }
    }

    public String getPlayerFishingArea(int row, int col){
        if (currentMap.equals("farm")) {
            return "Pond";
        }
        else{
            if (col > 5 && col < 42 && row > 111 && row < 136){
                return "Mountain Lake";
            } else if (row < 50) {
                return "Ocean";
            } else if (col > 182 && row > 50) {
                return "Forest River";
            }
        }
        return null;
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

        //draw energy
        g2.setColor(Color.white);
        g2.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        g2.drawString("Energy : " + Integer.toString(player.getEnergy()), 10, 25);
    }

    public PlantManager getPlantManager() {
        return plantManager;
    }
}
