package com.spakborhills.model.items;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> playerInventory; //<item, jumlah>

    public Inventory() {
        playerInventory = new HashMap<Item, Integer>();
    }

    public void add(Item item, int quantity) {
        playerInventory.put(item, playerInventory.getOrDefault(item, 0) + quantity);
    }

    public void use(Item item, int quantity) {
        if (playerInventory.containsKey(item)) {
            playerInventory.put(item, playerInventory.get(item) - quantity);
            playerInventory.remove(item, 0);
        } else {
            System.out.println("Item not found");
        }
    }
        // New method: removeItem (more explicit for removing items by name)
    public void removeItem(String itemName, int quantity) {
        Item itemToRemove = null;
        for (Map.Entry<Item, Integer> entry : playerInventory.entrySet()) {
            if (entry.getKey().getName().equals(itemName)) {
                itemToRemove = entry.getKey();
                break;
            }
        }

        if (itemToRemove != null && playerInventory.getOrDefault(itemToRemove, 0) >= quantity) {
            playerInventory.put(itemToRemove, playerInventory.get(itemToRemove) - quantity);
            // Remove item from map if quantity drops to 0 or less
            playerInventory.entrySet().removeIf(entry -> entry.getValue() <= 0);
        } else if (itemToRemove != null) {
            System.out.println("Not enough " + itemName + " in inventory.");
        } else {
            System.out.println(itemName + " not found in inventory.");
        }
    }

    // New method: hasItem (check if player has a specific item and quantity)
    public boolean hasItem(String itemName, int quantity) {
        for (Map.Entry<Item, Integer> entry : playerInventory.entrySet()) {
            if (entry.getKey().getName().equals(itemName)) {
                return entry.getValue() >= quantity;
            }
        }
        return false;
    }

    // New method: hasIngredients (check if player has all ingredients for a recipe)
    public boolean hasIngredients(Map<String, Integer> ingredients) { // Changed key to String for item name
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            String requiredItemName = entry.getKey();
            int requiredQuantity = entry.getValue();
            if (!hasItem(requiredItemName, requiredQuantity)) {
                System.out.println("Missing ingredient: " + requiredItemName + " x" + requiredQuantity);
                return false;
            }
        }
        return true;
    }

    // New method: removeIngredients (deduct all ingredients after cooking)
    public void removeIngredients(Map<String, Integer> ingredients) { // Changed key to String for item name
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            removeItem(entry.getKey(), entry.getValue());
        }
    }

    public Map<Item, Integer> getPlayerInventory() {
        return playerInventory;
    }


}
