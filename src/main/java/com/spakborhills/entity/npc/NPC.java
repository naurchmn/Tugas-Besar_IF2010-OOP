package com.spakborhills.entity.npc;

import java.util.*;
import com.spakborhills.items.Item;

public abstract class NPC {
    private String name;
    private int heartPoints;
    private static int maxHeartPoints = 150;
    private List<Item> lovedItems = new ArrayList<Item>();
    private List<Item> hatedItems = new ArrayList<Item>();
    private List<Item> likedItems = new ArrayList<Item>();
    private RelationshipStatus relationshipStatus;

    public NPC(String name, int heartPoints, List<Item> lovedItems, List<Item> hatedItems,
               List<Item> likedItems, RelationshipStatus relationshipStatus) throws NoSuchElementException {

        /* if items in lovedList, likedList, or hatedList is not in items (gatau perlu apa ngga)
        for (Object i : lovedItems) {
            if (!(i instanceof Item)) throw new NoSuchElementException("Item is currently not available");
        }
        for (Object j : hatedItems) {
            if (!(j instanceof Item)) throw new NoSuchElementException("Item is currently not available");
        }
        for (Object k : likedItems) {
            if (!(k instanceof Item)) throw new NoSuchElementException("Item is currently not available");
        }*/

        this.name = name;
        this.heartPoints = 0;
        this.lovedItems = lovedItems;
        this.hatedItems = hatedItems;
        this.likedItems = likedItems;
        this.relationshipStatus = RelationshipStatus.SINGLE;

    }

    public String getName() {
        return name;
    }

    // heart points methods
    public int getHeartPoints() {
        return heartPoints;
    }
    public static int getMaxHeartPoints() {
        return maxHeartPoints;
    }
    public void setHeartPoints(int heartPoints) {
        this.heartPoints = heartPoints;
    }

    // relationship status methods
    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }
    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    // list methods
    public List<Item> getLovedItems() {
        return lovedItems;
    }
    public List<Item> getHatedItems() {
        return hatedItems;
    }
    public List<Item> getLikedItems() {
        return likedItems;
    }
}
