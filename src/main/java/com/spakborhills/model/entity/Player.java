package com.spakborhills.model.entity;

import com.spakborhills.model.entity.NPCRegistry;
import com.spakborhills.model.items.Inventory;

public class Player {
    private String name;
    private String gender;
    private int maxEnergy = 100;
    private int energy;
    private String FarmName;
    //private NPC partner;
    private int gold;
    private Inventory inventory;
    // location

    public Player(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.energy = maxEnergy;
        this.inventory = new Inventory();
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
