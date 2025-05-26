package com.spakborhills.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.model.game.Tile;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        URL resourceUrl = getClass().getResource("/assets/Map/Farm.txt");
        if (resourceUrl == null) {
            System.err.println("Could not find map file in classpath");
        } else {
            System.out.println("Found map at: " + resourceUrl);
            loadMap("/assets/Map/Farm.txt");
        }

    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/000.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/001.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/002.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/003.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/004.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/005.png")));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/006.png")));

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

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayer().getWorldx() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldy() + gp.getPlayer().getScreenY();

            if (worldX + gp.getTileSize() > gp.getPlayer().getWorldx() - gp.getPlayer().getScreenX() &&
                    worldX - gp.getTileSize() < gp.getPlayer().getWorldx() + gp.getPlayer().getScreenX() &&
                    worldY + gp.getTileSize() > gp.getPlayer().getWorldy() - gp.getPlayer().getScreenY() &&
                    worldY - gp.getTileSize() < gp.getPlayer().getWorldy() + gp.getPlayer().getScreenY()) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
