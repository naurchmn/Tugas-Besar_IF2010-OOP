package com.spakborhills.model.entity;

import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.controller.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    private int screenX, screenY;
    private int worldx, worldy;
    private int speed;
    private final String name;

    private BufferedImage sprite;
    private String direction;

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH, String name) {
       this.gp = gp;
       this.keyH = keyH;
       this.name = name;

       screenX = gp.screenWidth/2 - gp.getTileSize()/2;
       screenY = gp.screenHeight/2 - gp.getTileSize()/2;

       setDefaultValues();
       getPlayerImage();
    }

    public void setDefaultValues(){
        worldx = gp.getTileSize() * gp.maxWorldCol / 2;
        worldy = gp.getTileSize() * gp.maxWorldRow / 2;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try{
            sprite = ImageIO.read(getClass().getResource("/assets/sprites/dasco.jpg"));
        } catch (IOException e) {
            System.out.println("Error loading player image");
            e.printStackTrace();
        }
        catch (NullPointerException e){
            System.out.println("Error loading player image null");
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {

            if (keyH.isUpPressed()) {
                direction = "up";
                worldy -= speed;
            } else if (keyH.isLeftPressed()) {
                direction = "left";
                worldx -= speed;
            } else if (keyH.isDownPressed()) {
                direction = "down";
                worldy += speed;
            } else if (keyH.isRightPressed()) {
                direction = "right";
                worldx += speed;
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(sprite, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
    }

    public int getWorldx() {
        return worldx;
    }

    public int getWorldy() {
        return worldy;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
