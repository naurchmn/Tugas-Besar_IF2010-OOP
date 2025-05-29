package com.spakborhills.controller;

//import com.spakborhills.model.entity.NPCRegistry;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.view.gui.GamePanel;

import java.awt.*;

public class PlayerController {

    private Player player;
    private PlayerView drawPlayer;
    private GamePanel gp;

    public PlayerController(Player player, PlayerView drawPlayer, GamePanel gp) {
        this.player = player;
        this.drawPlayer = drawPlayer;
        this.gp = gp;
    }

    // FARM ACTION
    public void tilling(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak player
        int playerTileCol = drawPlayer.getWorldX() / gp.getTileSize();
        int playerTileRow = drawPlayer.getWorldY() / gp.getTileSize();

        if (currentTileType.equals("000.png")) { // Tilling dilakukan hanya jika player berada di atas tile 000.png
            // Tile diubah menjadi 004.png setelah tilling
            gp.tileM.changeTile(playerTileCol, playerTileRow, "004.png");
        } else {
            System.out.println("You cannot do tilling here"); // Jika tidak berada di atas tile 000.png maka olayer tidak dapat melakukan tilling
        }
    }

    public void recoverLand(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = drawPlayer.getWorldX() / gp.getTileSize();
        int playerTileRow = drawPlayer.getWorldY() / gp.getTileSize();

        gp.tileM.changeTile(playerTileCol, playerTileRow, "000.png");
    }

    public void planting(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = drawPlayer.getWorldX() / gp.getTileSize();
        int playerTileRow = drawPlayer.getWorldY() / gp.getTileSize();

        if (currentTileType.equals("004.png")) { // Kalau sekarang tilenya 004.png
            gp.tileM.changeTile(playerTileCol, playerTileRow, "148.png");
        } else if (currentTileType.equals("147.png")) { // Kalau sekarang tilenya 147.png
            gp.tileM.changeTile(playerTileCol, playerTileRow, "146.png");
        } else {
            System.out.println("Cannot till this tile: " + currentTileType);
        }
    }

    public void watering(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = drawPlayer.getWorldX() / gp.getTileSize();
        int playerTileRow = drawPlayer.getWorldY() / gp.getTileSize();

        if (currentTileType.equals("004.png")) {
            gp.tileM.changeTile(playerTileCol, playerTileRow, "147.png");
        } else if (currentTileType.equals("148.png")) {
            gp.tileM.changeTile(playerTileCol, playerTileRow, "146.png");
        }
    }

    public void harvesting(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = drawPlayer.getWorldX() / gp.getTileSize();
        int playerTileRow = drawPlayer.getWorldY() / gp.getTileSize();

        this.recoverLand();
        gp.showTemporaryPopUp("/assets/PopUps/HarvestPopUp.png", 2500);
    }

    // AT HOUSE ACTION
    public void eating(){}
    public void cooking(){}
    public void sleeping(){}
    public void watching(){}

    // WORLD ACTION
    public void fishing(){
        System.out.println("You are fishing"); // Tetap di konsol untuk debug
    }
//    public void proposing(NPCRegistry npc){}
    public void marrying(){}
    public void getInTheHouse(){
        System.out.println("You get in the house");
        gp.setCurrentMap("house");
    }
    public void getOutTheHouse(){
        System.out.println("You get out the house");
        gp.returnToPreviousMap();
    }
    public void gifting(){}
    public void selling(){}

    // getter setter
    public PlayerView getPlayerView() {
        return drawPlayer;
    }

}
