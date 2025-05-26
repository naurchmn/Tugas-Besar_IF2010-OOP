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

        URL resourceUrl = getClass().getResource("/assets/WorldMaps/WorldMaps");
        if (resourceUrl == null) {
            System.out.println("Could not find map file in classpath");
        } else {
            System.out.println("Found map at: " + resourceUrl);
            loadMap("/assets/WorldMaps/WorldMaps");
        }

    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++){
            String fileName;
            boolean tileCol;

            fileName = fileNames.get(i);

            if (tileCols.get(i).equals("true")) {
                tileCol = true;
            } else {
                tileCol = false;
            }

            setup(i, fileName, tileCol);
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

    public void loadMap(String fileMap) {
        try {
            InputStream is = getClass().getResourceAsStream(fileMap);
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

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // STOPING AT EDGE
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            } if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }

            int rightOffSet = gp.screenWidth - gp.player.screenX;
            if (rightOffSet > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffSet = gp.screenHeight - gp.player.screenY;
            if (bottomOffSet > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            // DRAW THE TILE
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            } else if (gp.player.screenX > gp.player.worldX ||
                    gp.player.screenY > gp.player.worldY ||
                    rightOffSet > gp.worldWidth - gp.player.worldX ||
                    bottomOffSet > gp.worldHeight - gp.player.worldY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
