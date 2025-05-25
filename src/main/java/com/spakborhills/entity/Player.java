package com.spakborhills.entity;

import com.spakborhills.gui.GamePanel;
import com.spakborhills.gui.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2 ;

        solidArea = new Rectangle(12, 18, 18, 27);

        setDefaultValues();
        getPlayerImage(); // Call the method to load the image
    }

    public void getPlayerImage() {
        try {
            front1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar00.png")));
            front2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar01.png")));
            front3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar00.png")));
            front4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar02.png")));
            back1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar03.png")));
            back2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar04.png")));
            back3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar03.png")));
            back4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar05.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar06.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar07.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar06.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar08.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar09.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar10.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar09.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/Aymar/aymar11.png")));

        } catch (IOException e) {
            System.out.println("Gagal load gambar!");
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 16;
        worldY = gp.tileSize * 11;
        speed = 3;
        direction = "down";
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // IF COLLISION IS FLASEPLAYER CAN MOVE
            if (collisionOn == false) {
                switch(direction) {
                    case "up" : worldY -= speed; break;
                    case "down" : worldY += speed; break;
                    case "left" : worldX -= speed; break;
                    case "right" : worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "down" :
                if (spriteNum == 1) {
                    image = front1;
                } else if (spriteNum == 2) {
                    image = front2;
                } else if (spriteNum == 3) {
                    image = front3;
                } else {
                    image = front4;
                }
                break;
            case "up" :
                if (spriteNum == 1) {
                    image = back1;
                } else if (spriteNum == 2) {
                    image = back2;
                } else if (spriteNum == 3) {
                    image = back3;
                } else {
                    image = back4;
                }
                break;
            case "left" :
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                } else if (spriteNum == 3) {
                    image = left3;
                } else {
                    image = left4;
                }
                break;
            case "right" :
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                } else if (spriteNum == 3) {
                    image = right3;
                } else {
                    image = right4;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}

