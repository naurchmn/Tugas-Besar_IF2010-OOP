package com.spakborhills.view.npc;

import com.spakborhills.model.entity.Entity;
import com.spakborhills.model.entity.NPC;
import com.spakborhills.model.entity.NPCRegistry;
import com.spakborhills.view.gui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class NPCView extends Entity {

    private NPC npc;
    private static final Map<String, BufferedImage> spriteCache = new HashMap<>();
    private BufferedImage sprite;
    private GamePanel gp;

    public NPCView(GamePanel gp, String npcName) {
        super(gp);

        this.npc = NPCRegistry.createNPC(npcName);
        this.direction = "down";
        this.speed = 4;
        getNPCImage(npcName);
    }

    public void getNPCImage(String npcName) {
        try {
            if (!spriteCache.containsKey(npcName)) {
                String imagePath = "/assets/sprites/" + npcName.toLowerCase() + ".jpg";
                BufferedImage sprite = ImageIO.read(getClass().getResource(imagePath));
                spriteCache.put(npcName, sprite);
            }
            this.front1 = spriteCache.get(npcName);
        } catch (IOException e) {
            System.out.println("Gagal load gambar!");
            e.printStackTrace();
        }

    }

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

    public NPC getNPC() {return npc;}
}
