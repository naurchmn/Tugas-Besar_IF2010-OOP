package com.spakborhills.model.entity;

import java.util.*;
import com.spakborhills.model.items.Item;

public class NPC implements Cloneable {
    private String name;
    private int heartPoints;
    private static int maxHeartPoints = 150;
    private List<Item> lovedItems;
    private List<Item> hatedItems;
    private List<Item> likedItems = new ArrayList<Item>();
    private RelationshipStatus relationshipStatus;

    public NPC(String name) {

        this.name = name;
        this.heartPoints = 0;
        this.lovedItems = new ArrayList<Item>();
        this.hatedItems = new ArrayList<Item>();
        this.likedItems = new ArrayList<Item>();
        this.relationshipStatus = RelationshipStatus.SINGLE;

    }

    // method clone
    @Override
    public NPC clone(){
        try {
            NPC cloned = (NPC) super.clone();
            cloned.lovedItems = new ArrayList<>(this.lovedItems);
            cloned.hatedItems = new ArrayList<>(this.hatedItems);
            cloned.likedItems = new ArrayList<>(this.likedItems);
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cannot clone NPC", e);
        }
    }

    // builder methods
    public NPC addLovedItem(Collection<Item> items) {
        lovedItems.addAll(items);
        return this;
    }
    public NPC addHatedItem(Collection<Item> items) {
        hatedItems.addAll(items);
        return this;
    }
    public NPC addLikedItem(Collection<Item> items) {
        likedItems.addAll(items);
        return this;
    }

    // getters
    public String getName() {
        return name;
    }
    public int getHeartPoints() {
        return heartPoints;
    }
    public static int getMaxHeartPoints() {
        return maxHeartPoints;
    }
    public RelationshipStatus getRelationshipStatus() {return relationshipStatus;}
    public List<Item> getLovedItems() {
        return lovedItems;
    }
    public List<Item> getHatedItems() {
        return hatedItems;
    }
    public List<Item> getLikedItems() {
        return likedItems;
    }

    // setters
    public void setHeartPoints(int heartPoints) {
        this.heartPoints = heartPoints;
    }
    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }
}
