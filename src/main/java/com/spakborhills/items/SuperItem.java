package com.spakborhills.items;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.spakborhills.gui.GamePanel;
import java.awt.Rectangle;

public class SuperItem {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
//    public Rectangle solidArea;
//    public int solidAreaDefaultX = 0;
//    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp, int scale) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        int objSize = gp.originalTileSize * scale;

//        Rectangle solidArea = new Rectangle(worldX, worldY, objSize, objSize);

        if (worldX + objSize > gp.player.worldX - gp.player.screenX &&
                worldX - objSize < gp.player.worldX + gp.player.screenX &&
                worldY + objSize > gp.player.worldY - gp.player.screenY &&
                worldY - objSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, objSize, objSize, null);
        }
    }
}

