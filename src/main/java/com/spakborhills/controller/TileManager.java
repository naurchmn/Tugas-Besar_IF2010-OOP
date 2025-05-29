package com.spakborhills.controller;

import java.io.*;
import java.awt.*;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import com.spakborhills.model.game.Tile;
import com.spakborhills.view.gui.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> tileCols = new ArrayList<>();
    private String loadedMap;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // READ TILE DATA
        InputStream is = getClass().getResourceAsStream("/assets/WorldMaps/TileData");
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

        // GETTING TILE NAME AND COLLISION
        String line;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                tileCols.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++){
            String tileName;
            boolean tileCol;

            tileName = fileNames.get(i);

            if (tileCols.get(i).equals("true")) {
                tileCol = true;
            } else {
                tileCol = false;
            }

            setup(i, tileName, tileCol);
        }
    }

    public void setup(int index, String imageName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/" + imageName)));
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String loadedMap) {
        try {
            InputStream is = getClass().getResourceAsStream(loadedMap);
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine(); // just read the number including the space

                String[] numbers = line.split(" "); // input the number into the array with space seperator

                while (col < gp.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]); // changing the String numbers[] into integer

                    mapTileNum[col][row] = num;
                    col++;
                }
                col = 0;
                row++;
            }

            br.close();

        } catch (Exception e) {}
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayerView().worldX + gp.getPlayerView().getScreenX();
            int screenY = worldY - gp.getPlayerView().worldY + gp.getPlayerView().getScreenY();

            // STOPING AT EDGE
            if (gp.getPlayerView().getScreenX() > gp.getPlayerView().worldX) {
                screenX = worldX;
            } if (gp.getPlayerView().getScreenY() > gp.getPlayerView().worldY) {
                screenY = worldY;
            }

            int rightOffSet = gp.screenWidth - gp.getPlayerView().getScreenX();
            if (rightOffSet > gp.worldWidth - gp.getPlayerView().worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffSet = gp.screenHeight - gp.getPlayerView().getScreenY();
            if (bottomOffSet > gp.worldHeight - gp.getPlayerView().worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            // DRAW THE TILE
            if (worldX + gp.getTileSize() > gp.getPlayerView().worldX - gp.getPlayerView().getScreenX() &&
                    worldX - gp.getTileSize() < gp.getPlayerView().worldX + gp.getPlayerView().getScreenX() &&
                    worldY + gp.getTileSize() > gp.getPlayerView().worldY - gp.getPlayerView().getScreenY() &&
                    worldY - gp.getTileSize() < gp.getPlayerView().worldY + gp.getPlayerView().getScreenY()) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            } else if (gp.getPlayerView().getScreenX() > gp.getPlayerView().worldX ||
                    gp.getPlayerView().getScreenY() > gp.getPlayerView().worldY ||
                    rightOffSet > gp.worldWidth - gp.getPlayerView().worldX ||
                    bottomOffSet > gp.worldHeight - gp.getPlayerView().worldY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
    public String getLoadedMap() {
        return loadedMap;
    }

    public void setLoadedMap(String loadedMap) {
        this.loadedMap = loadedMap;
    }

    // Metode baru untuk mendapatkan jenis tile di posisi pemain
    public String getTileTypeAtPlayerPosition(int worldX, int worldY) {
        int tileCol = worldX / gp.getTileSize();
        int tileRow = worldY / gp.getTileSize();

        if (tileCol >= 0 && tileCol < gp.maxWorldCol && tileRow >= 0 && tileRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[tileCol][tileRow];
            return fileNames.get(tileNum); // Mengembalikan nama file tile
        }
        return "Unknown"; // Jika di luar batas
    }
}
