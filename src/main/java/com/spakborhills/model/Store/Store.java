package com.spakborhills.model.stores;

import com.spakborhills.model.entity.Player;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.crops.Crops;
import com.spakborhills.model.items.crops.CropsRegistry;
import com.spakborhills.model.items.recipes.Recipe;
import com.spakborhills.model.items.recipes.RecipeRegistry;
import com.spakborhills.model.items.seeds.Seed;
import com.spakborhills.model.items.seeds.SeedRegistry;

import java.util.ArrayList;
import java.util.List;

public class Store { // Renamed from GeneralStore
    private List<Item> itemsForSale;
    private String storeName;

    public Store(String storeName) {
        this.storeName = storeName;
        this.itemsForSale = new ArrayList<>();
        loadStoreInventory();
    }

    private void loadStoreInventory() {
        // Add all available crops from CropsRegistry
        for (String cropName : CropsRegistry.getAvailableCropsNames()) {
            Crops crop = CropsRegistry.getCropsPrototype(cropName);
            if (crop != null) {
                itemsForSale.add(crop);
            }
        }

        // Add all available seeds from SeedRegistry
        for (String seedName : SeedRegistry.getAvailableSeedNames()) {
            Seed seed = SeedRegistry.getSeedPrototype(seedName);
            if (seed != null) {
                itemsForSale.add(seed);
            }
        }

        // Add specific recipes (recipe_1 and recipe_10)
        // Note: These are 'Recipe' objects which now extend 'Item', so they can be sold.
        Recipe fishChipsRecipe = RecipeRegistry.getRecipePrototype("Fish n' Chips Recipe");
        if (fishChipsRecipe != null) {
            itemsForSale.add(fishChipsRecipe);
        }

        Recipe fishSandwichRecipe = RecipeRegistry.getRecipePrototype("Fish Sandwich Recipe");
        if (fishSandwichRecipe != null) {
            itemsForSale.add(fishSandwichRecipe);
        }

        System.out.println(storeName + " loaded with " + itemsForSale.size() + " items.");
    }

    public List<Item> getItemsForSale() {
        return itemsForSale;
    }

    public void displayItems() {
        System.out.println("\n--- " + storeName + " Items for Sale ---");
        for (int i = 0; i < itemsForSale.size(); i++) {
            Item item = itemsForSale.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (Buy: " + item.getBuyPrice() + "g)");
        }
        System.out.println("---------------------------------");
    }

    /**
     * Handles the purchase of an item from the store by a player.
     * If the item is a Recipe, it triggers the recipe unlock for the player.
     *
     * @param player The player attempting to buy the item.
     * @param itemToBuy The item prototype to buy.
     * @param quantity The quantity of the item to buy.
     * @return true if the purchase was successful, false otherwise.
     */
    public boolean buyItem(Player player, Item itemToBuy, int quantity) {
        if (itemToBuy == null || quantity <= 0) {
            System.out.println("Invalid item or quantity.");
            return false;
        }

        // Find the actual item in the store's inventory to get correct price and type
        Item storeItem = null;
        for (Item item : itemsForSale) {
            if (item.getName().equalsIgnoreCase(itemToBuy.getName())) {
                storeItem = item;
                break;
            }
        }

        if (storeItem == null) {
            System.out.println(itemToBuy.getName() + " is not sold in this store.");
            return false;
        }

        int totalPrice = storeItem.getBuyPrice() * quantity;
        if (player.getInventory().getMoney() >= totalPrice) {
            player.getInventory().deductMoney(totalPrice);
            for (int i = 0; i < quantity; i++) {
                // If it's a recipe, unlock it for the player
                if (storeItem instanceof Recipe) {
                    Recipe recipeToUnlock = (Recipe) storeItem;
                    if (!player.hasRecipeUnlocked(recipeToUnlock.getId())) { // Assumes Player has hasRecipeUnlocked method
                        player.unlockRecipe(recipeToUnlock.getId()); // Assumes Player has unlockRecipe method
                    } else {
                        System.out.println(recipeToUnlock.getName() + " is already unlocked.");
                    }
                } else {
                    // Add the item to player's inventory
                    // It's crucial that all Item subclasses implement clone() properly
                    player.getInventory().addItem(storeItem.clone());
                }
            }
            System.out.println("Successfully bought " + quantity + " " + storeItem.getName() + "(s) for " + totalPrice + "g.");
            return true;
        } else {
            System.out.println("Not enough money to buy " + storeItem.getName() + ".");
            return false;
        }
    }

    public String getStoreName() {
        return storeName;
    }
}
