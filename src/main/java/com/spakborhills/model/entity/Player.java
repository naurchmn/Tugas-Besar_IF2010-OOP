package com.spakborhills.model.entity;

//import com.spakborhills.model.entity.npc.NPCRegistry;
import com.spakborhills.model.items.Inventory;
import com.spakborhills.model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String name;
    private String gender;
    private final int maxEnergy = 100;
    private int energy;
    private String farmName;
    //private NPC partner;
    private int gold;
    private Inventory inventory;
    private Item itemHeld = null;

    // location

    public Player(String name, String gender, String farmName) {
        this.name = name;
        this.gender = gender;
        this.farmName = farmName;
        this.energy = maxEnergy;
        inventory = new Inventory();
        inventory.add(new Item("Hoe"), 1);
        inventory.add(new Item("Watering Can"), 1);
        inventory.add(new Item("Pickaxe"), 1);
        inventory.add(new Item("Fishing Rod"), 1);
    }

    public int getEnergy() {
        return energy;
    }
    public int getGold() {
        return gold;
    }
    public int getMaxEnergy(){
        return maxEnergy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }


    public void setGold(int gold) {
        this.gold = gold;
    }

    public Item getItemHeld(){
        return itemHeld;
    }

    public void setItemHeld(Item item){
        this.itemHeld = item;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
