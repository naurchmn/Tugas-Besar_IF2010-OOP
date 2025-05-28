package com.spakborhills.model.entity;

//import com.spakborhills.model.entity.npc.NPCRegistry;
import com.spakborhills.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private String gender;
    private final int maxEnergy = 100;
    private int energy;
    private String FarmName;
    //private NPC partner;
    private int gold;
    private List<Item> inventory;
    // location

    public Player(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.energy = maxEnergy;
        inventory = new ArrayList<>();
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
