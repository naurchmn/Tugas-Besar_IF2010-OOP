package com.spakborhills.entity.npc;

import java.util.AbstractList;

public abstract class NPC {
    private String name;
    private int heartPoints;
    //private List<Items> lovedItems = new ArrayList<Items>();
    //private List<Items> hatedItems = new ArrayList<Items>();
    //private List<Items> likedItems = new ArrayList<Items>();
    private String relationshipStatus;

    public NPC(String name, int heartPoints) {
        this.name = name;
        this.heartPoints = 0;
        this.relationshipStatus = "Single";
    }

}
