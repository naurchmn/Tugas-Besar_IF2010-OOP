package com.spakborhills.model.entity;

import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.view.gui.KeyHandler;

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
            front1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/downidle.png")));
            front2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/down01.png")));
            front3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/downidle.png")));
            front4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/down02.png")));
            back1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/upidle.png")));
            back2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/up01.png")));
            back3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/upidle.png")));
            back4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/up02.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/leftidle.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/left01.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/leftidle.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/left02.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/rightidle.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/right01.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/rightidle.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/right02.png")));

        } catch (IOException e) {
            System.out.println("Gagal load gambar!");
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 216;
        worldY = gp.tileSize * 151;
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
//            gp.cChecker.checkTile(this);

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

        int x = screenX;
        int y = screenY;

        if (screenX > worldX) {
            x = worldX;
        } if (screenY > worldY) {
            y = worldY;
        }
        int rightOffSet = gp.screenWidth - screenX;
        if (rightOffSet > gp.worldWidth - worldX) {
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffSet = gp.screenHeight - screenY;
        if (bottomOffSet > gp.worldHeight - worldY) {
            y = gp.screenHeight - (gp.worldHeight - worldY);
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
}

