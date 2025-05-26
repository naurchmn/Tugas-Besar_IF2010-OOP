package com.spakborhills.model.entity;

import com.spakborhills.view.gui.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gp;

    private int worldx, worldy;
    public int speed;

    public BufferedImage front1, front2, front3, front4, back1, back2, back3, back4, right1, right2, right3, right4, left1, left2, left3, left4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = false;

    // CHARACTER ATTRIBUTES
    public int maxEnergy;
    public int energy;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public int getWorldy() {
        return worldy;
    }

    public void setWorldy(int worldy) {
        this.worldy = worldy;
    }

    public int getWorldx() {
        return worldx;
    }

    public void setWorldx(int worldx) {
        this.worldx = worldx;
    }
}
