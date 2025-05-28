package com.spakborhills.model.items;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.spakborhills.view.gui.GamePanel;

public class SuperItem {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
//    public Rectangle solidArea;
//    public int solidAreaDefaultX = 0;
//    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp, int scale) {
        int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().getScreenY();
        int objSize = gp.getTileSize() * scale;

//        Rectangle solidArea = new Rectangle(worldX, worldY, objSize, objSize);

        if (worldX + objSize > gp.getPlayer().worldX - gp.getPlayer().getScreenX() &&
                worldX - objSize < gp.getPlayer().worldX + gp.getPlayer().getScreenX() &&
                worldY + objSize > gp.getPlayer().worldY - gp.getPlayer().getScreenY() &&
                worldY - objSize < gp.getPlayer().worldY + gp.getPlayer().getScreenY()) {
            g2.drawImage(image, gp.getPlayer().getScreenX(), gp.getPlayer().getScreenY(), objSize, objSize, null);
        }
    }
}

