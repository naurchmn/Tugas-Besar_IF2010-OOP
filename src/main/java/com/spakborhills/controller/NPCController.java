package com.spakborhills.controller;

import com.spakborhills.model.entity.NPC;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.items.Item;
import com.spakborhills.view.npc.NPCView;

public class NPCController {
    private NPC npcModel;
    private NPCView npcView;
    private Player playerModel;
    private PlayerController playerController;

    public NPCController(NPC npcModel, NPCView npcView) {
        this.npcModel = npcModel;
        this.npcView = npcView;
    }

    public void updateHeartPoints(int points){
        int currentPoints = npcModel.getHeartPoints();
        int newPoints = Math.min(NPC.getMaxHeartPoints(),
                Math.max(0, currentPoints + points));
        npcModel.setHeartPoints(newPoints);
    }

    public void gotGift(Item item, Player player) {}
    public void gotProporsed(Player player) {}
    public void marryPlayer(Player player) {}
    public void chatWithPlayer(Player player) {}

}
