package com.spakborhills.model.entity;

import java.util.*;
import com.spakborhills.model.items.Item;

public class NPCRegistry {
    private static final Map<String, NPC> npcPrototypes = new HashMap<>();

    /*static {
        // Mayor tadi
        NPC mayorTadi = new NPC("Mayor Tadi")
                .addLovedItem()
                .addLikedItem()
                .addHatedItem();
        registerNPC(mayorTadi);

        // Caroline
        NPC caroline = new NPC("Caroline")
                .addLovedItem()
                .addLikedItem()
                .addHatedItem();
        registerNPC(caroline);

        // Perry
        NPC perry = new NPC("perry")
                .addLovedItem()
                .addLikedItem()
                .addHatedItem();
        registerNPC(perry);

        // Dasco
        NPC dasco = new NPC("Dasco")
                .addLovedItem()
                .addLikedItem()
                .addHatedItem();
        registerNPC(dasco);

        // Emily
        NPC emily = new NPC("Emily")
                .addLovedItem()
                .addLikedItem()
                .addHatedItem();
        registerNPC(emily);

        // Abigail
        NPC abigail = new NPC("Abigail")
                .addLovedItem()
                .addLikedItem()
                .addHatedItem();
        registerNPC(abigail);


    }*/

    public static void registerNPC(NPC prototype) {
        npcPrototypes.put(prototype.getName(), prototype);
    }

    public static NPC createNPC(String name) {
        NPC prototype = npcPrototypes.get(name);
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype found for NPC: " + name);
        }
        return prototype.clone();
    }

    public static boolean hasPrototype(String name) {
        return npcPrototypes.containsKey(name);
    }

    public static Set<String> getAvailableNPCs() {
        return new HashSet<>(npcPrototypes.keySet());
    }

}
