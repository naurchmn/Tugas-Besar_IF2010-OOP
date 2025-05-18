package com.spakborhills.view.npc;

import com.spakborhills.view.gui.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPCView extends Entity {

    public NPCView(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 4;

        getNPCImage();
    }

    public void getNPCImage() {
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

    @Override
    public void setAction() {

        Random random = new Random();
        int i = random.nextInt(100)+1;

        if (i <= 25) {
            direction = "up";
        }
        if (i > 25 && i <= 50) {
            direction = "down";
        }
        if (i > 50 && i <= 75) {
            direction = "left";
        }
        if (i > 75) {
            direction = "right";
        }
    }
}
